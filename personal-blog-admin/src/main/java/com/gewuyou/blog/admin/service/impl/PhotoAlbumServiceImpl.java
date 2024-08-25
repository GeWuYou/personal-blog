package com.gewuyou.blog.admin.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gewuyou.blog.admin.mapper.PhotoAlbumMapper;
import com.gewuyou.blog.admin.mapper.PhotoMapper;
import com.gewuyou.blog.admin.service.IImageReferenceService;
import com.gewuyou.blog.admin.service.IPhotoAlbumService;
import com.gewuyou.blog.common.annotation.ReadLock;
import com.gewuyou.blog.common.constant.RedisConstant;
import com.gewuyou.blog.common.dto.PhotoAlbumAdminDTO;
import com.gewuyou.blog.common.dto.PhotoAlbumDTO;
import com.gewuyou.blog.common.entity.PageResult;
import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.exception.GlobalException;
import com.gewuyou.blog.common.model.Photo;
import com.gewuyou.blog.common.model.PhotoAlbum;
import com.gewuyou.blog.common.utils.BeanCopyUtil;
import com.gewuyou.blog.common.utils.CompletableFutureUtil;
import com.gewuyou.blog.common.utils.PageUtil;
import com.gewuyou.blog.common.vo.ConditionVO;
import com.gewuyou.blog.common.vo.PhotoAlbumVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import static com.gewuyou.blog.common.constant.CommonConstant.FALSE;
import static com.gewuyou.blog.common.constant.CommonConstant.TRUE;

/**
 * <p>
 * 相册 服务实现类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
@Service
public class PhotoAlbumServiceImpl extends ServiceImpl<PhotoAlbumMapper, PhotoAlbum> implements IPhotoAlbumService {

    private final PhotoMapper photoMapper;
    private final IImageReferenceService imageReferenceService;
    private final Executor asyncTaskExecutor;

    @Autowired
    public PhotoAlbumServiceImpl(PhotoMapper photoMapper, IImageReferenceService imageReferenceService, @Qualifier("asyncTaskExecutor") Executor asyncTaskExecutor) {
        this.photoMapper = photoMapper;
        this.imageReferenceService = imageReferenceService;
        this.asyncTaskExecutor = asyncTaskExecutor;
    }

    /**
     * 保存或更新相册
     *
     * @param photoAlbumVO 相册VO
     */
    @Override
    @ReadLock(RedisConstant.IMAGE_LOCK)
    public void saveOrUpdatePhotoAlbum(PhotoAlbumVO photoAlbumVO) {
        PhotoAlbum existPhotoAlbum = baseMapper.selectOne(
                new LambdaQueryWrapper<PhotoAlbum>()
                        .select(PhotoAlbum::getId, PhotoAlbum::getAlbumCover)
                        .eq(PhotoAlbum::getAlbumName, photoAlbumVO.getAlbumName())
        );
        if (Objects.nonNull(existPhotoAlbum) && !existPhotoAlbum.getId().equals(photoAlbumVO.getId())) {
            throw new GlobalException(ResponseInformation.ALBUM_NAME_EXIST);
        }
        PhotoAlbum photoAlbum = BeanCopyUtil.copyObject(photoAlbumVO, PhotoAlbum.class);
        String oldPhotoAlbumCover = Objects.isNull(existPhotoAlbum) ? null : existPhotoAlbum.getAlbumCover();
        String newPhotoAlbumCover = photoAlbumVO.getAlbumCover();
        CompletableFutureUtil.runAsyncWithExceptionAlly(asyncTaskExecutor,
                () -> imageReferenceService
                        .handleImageReference(newPhotoAlbumCover, oldPhotoAlbumCover),
                () -> this.saveOrUpdate(photoAlbum)
        );
    }

    /**
     * 获取后台相册列表
     *
     * @param conditionVO 条件
     * @return 分页结果
     */
    @Override
    public PageResult<PhotoAlbumAdminDTO> listPhotoAlbumsAdminDTOs(ConditionVO conditionVO) {
        return CompletableFuture.supplyAsync(
                        () -> baseMapper.listPhotoAlbumsAdminDTOs(new Page<>(PageUtil.getCurrent(), PageUtil.getSize()), conditionVO)
                        , asyncTaskExecutor)
                .thenApply(PageResult::new)
                .exceptionally(e -> {
                    log.error("获取后台相册列表失败", e);
                    return new PageResult<>();
                })
                .join();
    }

    /**
     * 获取后台相册信息列表
     *
     * @return 相册信息列表
     */
    @Override
    public List<PhotoAlbumDTO> listPhotoAlbumAdminDTOs() {
        List<PhotoAlbum> photoAlbums = baseMapper.selectList(
                new LambdaQueryWrapper<PhotoAlbum>()
                        .eq(PhotoAlbum::getIsDelete, FALSE)
        );
        return BeanCopyUtil.copyList(photoAlbums, PhotoAlbumDTO.class);
    }

    /**
     * 根据相册ID获取后台相册信息
     *
     * @param albumId 相册ID
     * @return 相册信息
     */
    @Override
    public PhotoAlbumAdminDTO getPhotoAlbumByIdAdminDTO(Integer albumId) {
        return CompletableFuture
                .supplyAsync(
                        () -> baseMapper.selectById(albumId), asyncTaskExecutor)
                .thenCombine(CompletableFuture.supplyAsync(
                        () -> baseMapper.selectCount(new LambdaQueryWrapper<PhotoAlbum>()
                                .eq(PhotoAlbum::getId, albumId)
                                .eq(PhotoAlbum::getIsDelete, FALSE)
                        ), asyncTaskExecutor), (photoAlbum, count) -> {
                    PhotoAlbumAdminDTO photoAlbumAdminDTO = BeanCopyUtil.copyObject(photoAlbum, PhotoAlbumAdminDTO.class);
                    photoAlbumAdminDTO.setPhotoCount(count);
                    return photoAlbumAdminDTO;
                })
                .exceptionally(e -> {
                    log.error("async exception", e);
                    throw new GlobalException(ResponseInformation.ASYNC_EXCEPTION);
                })
                .join();
    }

    /**
     * 根据相册ID删除相册
     *
     * @param albumId 相册ID
     */
    @Override
    public void deletePhotoAlbumById(Integer albumId) {
        Long count = photoMapper.selectCount(
                new LambdaQueryWrapper<Photo>()
                        .eq(Photo::getAlbumId, albumId)
        );
        // 判断相册下是否有照片存在,如果存在则逻辑删除，不存在说明是空相册物理删除
        if (count > 0) {
            CompletableFutureUtil.runAsyncWithExceptionAlly(asyncTaskExecutor,
                    () ->
                            baseMapper
                                    .updateById(
                                            PhotoAlbum.builder()
                                                    .id(albumId)
                                                    .isDelete(TRUE)
                                                    .build()
                                    ),
                    () -> photoMapper.update(new Photo(), new LambdaUpdateWrapper<Photo>()
                            .set(Photo::getIsDelete, TRUE)
                            .eq(Photo::getAlbumId, albumId))
            );
        } else {
            CompletableFutureUtil.runAsyncWithExceptionAlly(asyncTaskExecutor,
                    () ->
                            imageReferenceService.deleteImageReference(baseMapper.selectById(albumId).getAlbumCover()),
                    () -> baseMapper.deleteById(albumId)
            );
        }
    }
}
