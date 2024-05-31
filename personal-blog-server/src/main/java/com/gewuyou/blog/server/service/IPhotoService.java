package com.gewuyou.blog.server.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gewuyou.blog.common.dto.PhotoDTO;
import com.gewuyou.blog.common.model.Photo;

/**
 * <p>
 * 照片 服务类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
public interface IPhotoService extends IService<Photo> {

    /**
     * 根据相册id获取照片列表
     *
     * @param albumId 相册id
     * @return 照片列表
     */
    PhotoDTO listPhotoDTOsByAlbumId(Integer albumId);
}
