package com.gewuyou.blog.admin.service.impl;

import com.gewuyou.blog.admin.mapper.ImageReferenceMapper;
import com.gewuyou.blog.admin.service.IBlogQuartzJobService;
import com.gewuyou.blog.admin.service.IImageReferenceService;
import com.gewuyou.blog.admin.strategy.context.UploadStrategyContext;
import com.gewuyou.blog.common.annotation.WriteLock;
import com.gewuyou.blog.common.constant.RedisConstant;
import com.gewuyou.blog.common.model.ImageReference;
import com.gewuyou.blog.common.service.IRedisService;
import com.gewuyou.blog.common.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 博客定时任务服务实现
 * <p>
 * 此类用来处理定义需要Spring管理的定时任务逻辑
 *
 * @author gewuyou
 * @since 2024-08-09 12:10:17
 */
@Service
public class BlogQuartzJobServiceImpl implements IBlogQuartzJobService {
    private final IRedisService redisService;
    private final UploadStrategyContext uploadStrategyContext;
    private final IImageReferenceService imageReferenceService;
    private final ImageReferenceMapper imageReferenceMapper;

    @Autowired
    public BlogQuartzJobServiceImpl(IRedisService redisService, UploadStrategyContext uploadStrategyContext, IImageReferenceService imageReferenceService, ImageReferenceMapper imageReferenceMapper) {
        this.redisService = redisService;
        this.uploadStrategyContext = uploadStrategyContext;
        this.imageReferenceService = imageReferenceService;
        this.imageReferenceMapper = imageReferenceMapper;
    }

    /**
     * 清理临时图片
     */
    @Override
    @WriteLock(RedisConstant.IMAGE_LOCK)
    public void clearTempImage() {
        Set<Object> objects = redisService.sDiff(RedisConstant.TEMP_IMAGE_NAME, RedisConstant.DB_IMAGE_NAME);
        for (Object object : objects) {
            uploadStrategyContext.executeDeleteStrategy((String) object);
        }
        // 清理缓存
        redisService.delete(RedisConstant.TEMP_IMAGE_NAME);
        redisService.delete(RedisConstant.DB_IMAGE_NAME);
    }

    /**
     * 清理未引用的图片
     */
    @Override
    @WriteLock(RedisConstant.IMAGE_LOCK)
    public void clearNotReferenceImage() {
        // 查询所有引用数为0的图片引用集合
        List<ImageReference> notUsedImageReferences = imageReferenceService.getNotUsedImageReferences();
        // 删除图片文件
        for (ImageReference imageReference : notUsedImageReferences) {
            uploadStrategyContext.executeDeleteStrategy(FileUtil.getFilePathByUrl(imageReference.getImageUrl()));
        }
        // 删除数据库记录
        imageReferenceMapper.deleteBatchIds(notUsedImageReferences.stream().map(ImageReference::getId).toList());
    }
}
