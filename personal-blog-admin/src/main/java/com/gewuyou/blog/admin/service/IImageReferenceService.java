package com.gewuyou.blog.admin.service;

import com.gewuyou.blog.common.model.ImageReference;

import java.util.List;

/**
 * 图像引用服务接口
 *
 * @author gewuyou
 * @since 2024-08-09 23:01:50
 */
public interface IImageReferenceService {
    /**
     * 添加图像引用
     *
     * @param imageUrl 图像引用地址
     */
    void addImageReference(String imageUrl);

    /**
     * 批量添加图像引用
     *
     * @param imageUrls 图像引用地址列表
     */
    void addImageReference(List<String> imageUrls);

    /**
     * 删除图像引用
     *
     * @param imageUrl 图像引用地址
     */
    void deleteImageReference(String imageUrl);

    /**
     * 批量删除图像引用
     *
     * @param imageUrls 图像引用地址列表
     */
    void deleteImageReference(List<String> imageUrls);
    /**
     * 获取所有图像引用
     *
     * @return 图像引用列表
     */
    List<ImageReference> getNotUsedImageReferences();

    /**
     * 处理图片引用的增删逻辑
     *
     * @param newImageUrl 新的图片URL
     * @param oldImageUrl 旧的图片URL
     */
    void handleImageReference(String newImageUrl, String oldImageUrl);

    /**
     * 处理图片引用的增删逻辑
     *
     * @param newImageUrls 新的图片URL列表
     * @param oldImageUrls 旧的图片URL列表
     */
    void handleImageReferences(List<String> newImageUrls, List<String> oldImageUrls);
}
