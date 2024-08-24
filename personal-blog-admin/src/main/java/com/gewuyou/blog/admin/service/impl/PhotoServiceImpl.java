package com.gewuyou.blog.admin.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gewuyou.blog.admin.mapper.PhotoMapper;
import com.gewuyou.blog.admin.service.IImageReferenceService;
import com.gewuyou.blog.admin.service.IPhotoAlbumService;
import com.gewuyou.blog.admin.service.IPhotoService;
import com.gewuyou.blog.common.annotation.ReadLock;
import com.gewuyou.blog.common.constant.RedisConstant;
import com.gewuyou.blog.common.dto.PhotoAdminDTO;
import com.gewuyou.blog.common.entity.PageResult;
import com.gewuyou.blog.common.model.Photo;
import com.gewuyou.blog.common.model.PhotoAlbum;
import com.gewuyou.blog.common.service.IRedisService;
import com.gewuyou.blog.common.utils.BeanCopyUtil;
import com.gewuyou.blog.common.utils.CompletableFutureUtil;
import com.gewuyou.blog.common.utils.FileUtil;
import com.gewuyou.blog.common.utils.PageUtil;
import com.gewuyou.blog.common.vo.ConditionVO;
import com.gewuyou.blog.common.vo.DeleteVO;
import com.gewuyou.blog.common.vo.PhotoInfoVO;
import com.gewuyou.blog.common.vo.PhotoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

import static com.gewuyou.blog.common.constant.CommonConstant.FALSE;

/**
 * <p>
 * 照片 服务实现类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
@Service
public class PhotoServiceImpl extends ServiceImpl<PhotoMapper, Photo> implements IPhotoService {

    private final IPhotoAlbumService photoAlbumService;
    private final IRedisService redisService;
    private final IImageReferenceService imageReferenceService;
    private final Executor asyncTaskExecutor;

    @Autowired
    public PhotoServiceImpl(IPhotoAlbumService photoAlbumService, IRedisService redisService, IImageReferenceService imageReferenceService, @Qualifier("asyncTaskExecutor") Executor asyncTaskExecutor) {
        this.photoAlbumService = photoAlbumService;
        this.redisService = redisService;
        this.imageReferenceService = imageReferenceService;
        this.asyncTaskExecutor = asyncTaskExecutor;
    }

    /**
     * 分页查询照片列表
     *
     * @param conditionVO 条件
     * @return 分页结果
     */
    @Override
    public PageResult<PhotoAdminDTO> listPhotoAdminDTOs(ConditionVO conditionVO) {
        return CompletableFuture.supplyAsync(() -> baseMapper.selectPage(new Page<>(PageUtil.getCurrent(), PageUtil.getSize()), new LambdaQueryWrapper<Photo>()
                        .eq(Objects.nonNull(conditionVO.getAlbumId()), Photo::getAlbumId, conditionVO.getAlbumId())
                        .eq(Photo::getIsDelete, conditionVO.getIsDelete())
                        .orderByDesc(Photo::getId)
                        .orderByDesc(Photo::getUpdateTime)), asyncTaskExecutor)
                .thenApply(photoPage ->
                        new PageResult<>(BeanCopyUtil.copyList(photoPage.getRecords(), PhotoAdminDTO.class),
                                photoPage.getTotal()))
                .exceptionally(e -> {
                    log.error("查询照片列表失败", e);
                    return new PageResult<>();
                })
                .join();

    }

    /**
     * 更新照片信息
     *
     * @param photoInfoVO 照片信息
     */
    @Override
    @Async("asyncTaskExecutor")
    public void updatePhoto(PhotoInfoVO photoInfoVO) {
        Photo photo = BeanCopyUtil.copyObject(photoInfoVO, Photo.class);
        baseMapper.updateById(photo);
    }

    /**
     * 保存照片
     *
     * @param photoVO 照片
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @ReadLock(RedisConstant.IMAGE_LOCK)
    public void savePhotos(PhotoVO photoVO) {
        List<String> photoUrls = photoVO.getPhotoUrls();
        List<Photo> photoList = photoUrls
                .stream().map(item -> Photo.builder()
                        .albumId(photoVO.getAlbumId())
                        .photoName(IdWorker.getIdStr())
                        .photoSrc(item)
                        .build())
                .collect(Collectors.toList());
        this.saveBatch(photoList);
        // 使用事务完成事件，确保事务完成后异步任务才会执行
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                CompletableFutureUtil.runAsyncWithExceptionAlly(
                        () -> imageReferenceService.addImageReference(photoUrls), asyncTaskExecutor);
                CompletableFutureUtil.runAsyncWithExceptionAlly(() ->
                        redisService.sAdd(RedisConstant.DB_IMAGE_NAME, photoUrls.stream().map(FileUtil::getFilePathByUrl).toArray()), asyncTaskExecutor);
            }
        });
    }

    /**
     * 移动照片到指定相册
     *
     * @param photoVO 照片
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePhotosAlbum(PhotoVO photoVO) {
        List<Photo> photoList = photoVO.getPhotoIds().stream().map(item -> Photo.builder()
                        .id(item)
                        .albumId(photoVO.getAlbumId())
                        .build())
                .collect(Collectors.toList());
        this.updateBatchById(photoList);
    }

    /**
     * 更新照片删除状态
     *
     * @param deleteVO 删除VO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePhotoDelete(DeleteVO deleteVO) {
        List<Photo> photoList = deleteVO
                .getIds()
                .stream()
                .map(item -> Photo.builder()
                        .id(item.intValue())
                        .isDelete(deleteVO.getIsDelete())
                        .build())
                .collect(Collectors.toList());
        this.updateBatchById(photoList);
        // 更新相册删除状态
        if (deleteVO.getIsDelete().equals(FALSE)) {
            List<PhotoAlbum> photoAlbumList = baseMapper.selectList(new LambdaQueryWrapper<Photo>()
                            .select(Photo::getAlbumId)
                            .in(Photo::getId, deleteVO.getIds())
                            .groupBy(Photo::getAlbumId))
                    .stream()
                    .map(item -> PhotoAlbum.builder()
                            .id(item.getAlbumId())
                            .isDelete(FALSE)
                            .build())
                    .collect(Collectors.toList());
            photoAlbumService.updateBatchById(photoAlbumList);
        }
    }

    /**
     * 批量删除照片
     *
     * @param photoIds 照片ID列表
     */
    @Override
    public void deletePhotos(List<Integer> photoIds) {
        CompletableFutureUtil.supplyAsyncWithExceptionAlly(() -> baseMapper
                        .selectList(
                                new LambdaQueryWrapper<Photo>()
                                        .select(Photo::getPhotoSrc)
                                        .in(Photo::getId, photoIds))
                        .stream()
                        .map(Photo::getPhotoSrc)
                        .toList(), asyncTaskExecutor)
                .thenCompose(
                        imageUrls -> CompletableFuture.runAsync(
                                () -> imageReferenceService.deleteImageReference(imageUrls), asyncTaskExecutor));
        CompletableFutureUtil.runAsyncWithExceptionAlly(() -> baseMapper.deleteBatchIds(photoIds), asyncTaskExecutor);
    }
}
