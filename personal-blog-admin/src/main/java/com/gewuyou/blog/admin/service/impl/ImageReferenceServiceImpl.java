package com.gewuyou.blog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gewuyou.blog.admin.mapper.ImageReferenceMapper;
import com.gewuyou.blog.admin.service.IImageReferenceService;
import com.gewuyou.blog.common.constant.RedisConstant;
import com.gewuyou.blog.common.model.ImageReference;
import com.gewuyou.blog.common.service.IRedisService;
import com.gewuyou.blog.common.utils.CollectionUtil;
import com.gewuyou.blog.common.utils.FileUtil;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * 图像引用服务实现
 *
 * @author gewuyou
 * @since 2024-08-10 10:38:55
 */
@Service
public class ImageReferenceServiceImpl extends ServiceImpl<ImageReferenceMapper, ImageReference> implements IImageReferenceService {
    private final IRedisService redisService;


    public ImageReferenceServiceImpl(IRedisService redisService) {
        this.redisService = redisService;
    }

    /**
     * 保存或更新图像引用
     *
     * @param imageUrl 图像引用
     */
    @Override
    public void addImageReference(String imageUrl) {
        ImageReference imageReference = ImageReference
                .builder()
                .imageUrl(imageUrl)
                .build();
        baseMapper.insert(imageReference);
    }

    /**
     * 逻辑删除图像引用
     *
     * @param imageUrl 图像引用
     */
    @Override
    public void deleteImageReference(String imageUrl) {
        ImageReference imageReference = baseMapper.selectByImageUrl(imageUrl);
        if (Objects.isNull(imageReference)) {
            return;
        }
        imageReference.setIsDelete(Byte.valueOf("1"));
        baseMapper.updateById(imageReference);
    }

    /**
     * 获取所有失效的图像引用
     *
     * @return 失效的图像引用列表
     */
    @Override
    public List<ImageReference> getNotUsedImageReferences() {
        return baseMapper
                .selectList(new LambdaQueryWrapper<ImageReference>()
                        .select(ImageReference::getId, ImageReference::getImageUrl)
                        .eq(ImageReference::getIsDelete, Byte.valueOf("1")));
    }

    /**
     * 处理图片引用的增减逻辑
     *
     * @param newImageUrl 新的图片URL
     * @param oldImageUrl 旧的图片URL
     * @apiNote 处理图片引用的增删逻辑，包括保存图片文件名到redis并增加新引用删除旧引用 需要加上读锁，防止并发问题
     */
    @Override
    public void handleImageReference(String newImageUrl, String oldImageUrl) {
        // 如果图片地址不为空且不等于原地址，则保存图片文件名到redis并增加新引用删除旧引用
        if (Objects.nonNull(newImageUrl) && !newImageUrl.equals(oldImageUrl)) {
            // 将新图片URL添加到Redis中
            redisService.sAdd(RedisConstant.DB_IMAGE_NAME, FileUtil.getFilePathByUrl(newImageUrl));
            // 增加新图片的引用
            addImageReference(newImageUrl);
            // 删除旧图片的引用
            deleteImageReference(oldImageUrl);
        }
        // 如果新图片为空且旧图片存在，删除旧图片的引用
        if (Objects.isNull(newImageUrl) && Objects.nonNull(oldImageUrl)) {
            deleteImageReference(oldImageUrl);
        }
    }

    /**
     * 处理图片引用的增减逻辑
     *
     * @param newImageUrls 新图片URL列表
     * @param oldImageUrls 旧图片URL列表
     * @apiNote 处理图片引用的增删逻辑，包括保存图片文件名到redis并增加新引用删除旧引用 需要加上读锁，防止并发问题
     */
    @Override
    public void handleImageReferences(List<String> newImageUrls, List<String> oldImageUrls) {
        // 检索两个列表的差集，得到需要增加的图片URL列表
        Collection<String> newDifference = CollectionUtil.difference(newImageUrls, oldImageUrls);
        // 检索两个列表的差集，得到需要删除的图片URL列表
        Collection<String> oldDifference = CollectionUtil.difference(oldImageUrls, newImageUrls);
        // 处理需要增加的图片URL列表
        for (String imageUrl : newDifference) {
            handleImageReference(imageUrl, null);
        }
        // 处理需要删除的图片URL列表
        for (String imageUrl : oldDifference) {
            handleImageReference(null, imageUrl);
        }
    }
}
