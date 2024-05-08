package com.gewuyou.blog.server.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gewuyou.blog.common.model.PhotoAlbum;
import com.gewuyou.blog.server.mapper.PhotoAlbumMapper;
import com.gewuyou.blog.server.service.IPhotoAlbumService;
import org.springframework.stereotype.Service;

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

}
