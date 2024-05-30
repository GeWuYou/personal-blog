package com.gewuyou.blog.server.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gewuyou.blog.common.dto.PhotoAlbumDTO;
import com.gewuyou.blog.common.model.PhotoAlbum;

import java.util.List;

/**
 * <p>
 * 相册 服务类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
public interface IPhotoAlbumService extends IService<PhotoAlbum> {


    /**
     * 获取相册列表
     *
     * @return 相册列表
     */
    List<PhotoAlbumDTO> listPhotoAlbumDTOs();
}
