package com.gewuyou.blog.server.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gewuyou.blog.common.dto.PhotoAlbumDTO;
import com.gewuyou.blog.common.model.PhotoAlbum;
import com.gewuyou.blog.common.utils.BeanCopyUtil;
import com.gewuyou.blog.server.mapper.PhotoAlbumMapper;
import com.gewuyou.blog.server.service.IPhotoAlbumService;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.gewuyou.blog.common.constant.CommonConstant.FALSE;
import static com.gewuyou.blog.common.enums.ArticleStatusEnum.PUBLIC;

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

    /**
     * 获取相册列表
     *
     * @return 相册列表
     */
    @Override
    public List<PhotoAlbumDTO> listPhotoAlbumDTOs() {
        List<PhotoAlbum> photoAlbums = baseMapper.selectList(
                new LambdaQueryWrapper<PhotoAlbum>()
                        .eq(PhotoAlbum::getStatus, PUBLIC.getStatus())
                        .eq(PhotoAlbum::getIsDelete, FALSE)
                        .orderByDesc(PhotoAlbum::getId)
        );
        return BeanCopyUtil.copyList(photoAlbums, PhotoAlbumDTO.class);
    }
}
