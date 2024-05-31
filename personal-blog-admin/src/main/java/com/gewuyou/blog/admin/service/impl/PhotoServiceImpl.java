package com.gewuyou.blog.admin.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gewuyou.blog.admin.mapper.PhotoMapper;
import com.gewuyou.blog.admin.service.IPhotoAlbumService;
import com.gewuyou.blog.admin.service.IPhotoService;
import com.gewuyou.blog.common.dto.PageResultDTO;
import com.gewuyou.blog.common.dto.PhotoAdminDTO;
import com.gewuyou.blog.common.model.Photo;
import com.gewuyou.blog.common.model.PhotoAlbum;
import com.gewuyou.blog.common.utils.BeanCopyUtil;
import com.gewuyou.blog.common.utils.PageUtil;
import com.gewuyou.blog.common.vo.ConditionVO;
import com.gewuyou.blog.common.vo.DeleteVO;
import com.gewuyou.blog.common.vo.PhotoInfoVO;
import com.gewuyou.blog.common.vo.PhotoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
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

    @Autowired
    public PhotoServiceImpl(IPhotoAlbumService photoAlbumService) {
        this.photoAlbumService = photoAlbumService;
    }

    /**
     * 分页查询照片列表
     *
     * @param conditionVO 条件
     * @return 分页结果
     */
    @Override
    public PageResultDTO<PhotoAdminDTO> listPhotoAdminDTOs(ConditionVO conditionVO) {
        Page<Photo> page = new Page<>(PageUtil.getCurrent(), PageUtil.getSize());
        Page<Photo> photoPage = baseMapper.selectPage(page, new LambdaQueryWrapper<Photo>()
                .eq(Objects.nonNull(conditionVO.getAlbumId()), Photo::getAlbumId, conditionVO.getAlbumId())
                .eq(Photo::getIsDelete, conditionVO.getIsDelete())
                .orderByDesc(Photo::getId)
                .orderByDesc(Photo::getUpdateTime));
        List<PhotoAdminDTO> photoAdminDTOS = BeanCopyUtil.copyList(photoPage.getRecords(), PhotoAdminDTO.class);
        return new PageResultDTO<>(photoAdminDTOS, photoPage.getTotal());
    }

    /**
     * 更新照片信息
     *
     * @param photoInfoVO 照片信息
     */
    @Override
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
    public void savePhotos(PhotoVO photoVO) {
        List<Photo> photoList = photoVO.getPhotoUrls().stream().map(item -> Photo.builder()
                        .albumId(photoVO.getAlbumId())
                        .photoName(IdWorker.getIdStr())
                        .photoSrc(item)
                        .build())
                .collect(Collectors.toList());
        this.saveBatch(photoList);
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
        List<Photo> photoList = deleteVO.getIds().stream().map(item -> Photo.builder()
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
        baseMapper.deleteBatchIds(photoIds);
    }
}
