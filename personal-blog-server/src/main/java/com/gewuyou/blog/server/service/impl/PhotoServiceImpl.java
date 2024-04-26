package com.gewuyou.blog.server.service.impl;

import com.gewuyou.blog.server.entity.Photo;
import com.gewuyou.blog.server.mapper.PhotoMapper;
import com.gewuyou.blog.server.service.IPhotoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
