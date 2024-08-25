package com.gewuyou.blog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gewuyou.blog.admin.mapper.ImageReferenceMapper;
import com.gewuyou.blog.admin.service.IImageReferenceService;
import com.gewuyou.blog.common.constant.RedisConstant;
import com.gewuyou.blog.common.model.ImageReference;
import com.gewuyou.blog.common.service.IRedisService;
import com.gewuyou.blog.common.utils.CollectionUtil;
import com.gewuyou.blog.common.utils.CompletableFutureUtil;
import com.gewuyou.blog.common.utils.FileUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

import static com.gewuyou.blog.common.constant.CommonConstant.FALSE;
import static com.gewuyou.blog.common.constant.CommonConstant.TRUE;

/**
 * 图像引用服务实现
 *
 * @author gewuyou
 * @since 2024-08-10 10:38:55
 */
@Service
public class ImageReferenceServiceImpl extends ServiceImpl<ImageReferenceMapper, ImageReference> implements IImageReferenceService {
    private final IRedisService redisService;
    private final Executor asyncTaskExecutor;


    public ImageReferenceServiceImpl(IRedisService redisService, @Qualifier("asyncTaskExecutor") Executor asyncTaskExecutor) {
        this.redisService = redisService;
        this.asyncTaskExecutor = asyncTaskExecutor;
    }

    /**
     * 保存或更新图像引用
     *
     * @param imageUrl 图像引用
     */
    @Override
    @Async("asyncTaskExecutor")
    public void addImageReference(String imageUrl) {
        if (StringUtils.isBlank(imageUrl)) {
            return;
        }
        imageUrl = FileUtil.getFilePathByUrl(imageUrl);
        ImageReference imageReference = ImageReference
                .builder()
                .imageUrl(imageUrl)
                .isDelete(FALSE)
                .build();
        baseMapper.insert(imageReference);
    }

    /**
     * 批量添加图像引用
     *
     * @param imageUrls 图像引用地址列表
     * @apiNote 请注意：传入的url地址必须是引用地址而非网络地址
     */
    @Override
    public void addImageReference(List<String> imageUrls) {
        if (CollectionUtils.isEmpty(imageUrls)) {
            return;
        }
        List<ImageReference> imageReferences = imageUrls.stream()
                .map(imageUrl -> ImageReference
                        .builder()
                        .isDelete(FALSE)
                        .imageUrl(FileUtil.getFilePathByUrl(imageUrl))
                        .build())
                .toList();
        baseMapper.insertBatch(imageReferences);
    }

    /**
     * 逻辑删除图像引用
     *
     * @param imageUrl 图像引用网络地址
     */
    @Override
    @Async("asyncTaskExecutor")
    public void deleteImageReference(String imageUrl) {
        if (StringUtils.isBlank(imageUrl)) {
            return;
        }
        ImageReference imageReference = baseMapper.selectByImageUrl(FileUtil.getFilePathByUrl(imageUrl));
        if (Objects.isNull(imageReference)) {
            return;
        }
        imageReference.setIsDelete(TRUE);
        baseMapper.updateById(imageReference);
    }

    /**
     * 批量逻辑删除图像引用
     *
     * @param imageUrls 图像引用地址列表
     */
    @Override
    public void deleteImageReference(List<String> imageUrls) {
        if (CollectionUtils.isEmpty(imageUrls)) {
            return;
        }
        List<ImageReference> imageReferences = baseMapper.selectByImageUrls(
                imageUrls
                        .stream()
                        .map(FileUtil::getFilePathByUrl)
                        .toList()
        );
        if (CollectionUtils.isEmpty(imageReferences)) {
            return;
        }
        imageReferences.forEach(imageReference -> imageReference.setIsDelete(TRUE));
        baseMapper.updateBatchById(imageReferences);
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
                        .eq(ImageReference::getIsDelete, TRUE));
    }

    /**
     * 处理图片引用的增删逻辑
     *
     * @param newImageUrl 新的图片网络URL
     * @param oldImageUrl 旧的图片网络URL
     * @apiNote 处理图片引用的增删逻辑，包括保存图片文件名到redis并增加新引用删除旧引用 需要加上读锁，防止并发问题
     */
    @Override
    public void handleImageReference(String newImageUrl, String oldImageUrl) {
        if (StringUtils.isBlank(newImageUrl) && StringUtils.isBlank(oldImageUrl)) {
            return;
        }
        // 如果图片地址不为空且不等于原地址，则保存图片文件名到redis并增加新引用删除旧引用
        if (Objects.nonNull(newImageUrl) && !newImageUrl.equals(oldImageUrl)) {
            // 将新图片URL添加到Redis中
            redisService.sAdd(RedisConstant.DB_IMAGE_NAME, newImageUrl);
            // 增加新图片的引用
            addImageReference(newImageUrl);
            // 删除旧图片的引用
            deleteImageReference(oldImageUrl);
        }
        // 如果新图片为空且旧图片存在，删除旧图片的引用
        if (Objects.isNull(newImageUrl)) {
            deleteImageReference(oldImageUrl);
        }
    }

    /**
     * 处理图片引用的增删逻辑
     *
     * @param newImageUrls 新图片URL列表
     * @param oldImageUrls 旧图片URL列表
     * @apiNote 处理图片引用的增删逻辑，包括保存图片文件名到redis并增加新引用删除旧引用 需要加上读锁，防止并发问题 <br/>注意：传入的url地址必须是网络地址
     */
    @Override
    public void handleImageReference(List<String> newImageUrls, List<String> oldImageUrls) {
        // 如果两个列表都为空，则直接返回
        if (CollectionUtils.isEmpty(newImageUrls) && CollectionUtils.isEmpty(oldImageUrls)) {
            return;
        }
        // 如果新图片列表为空，则删除旧图片列表的引用
        if (CollectionUtils.isEmpty(newImageUrls)) {
            CompletableFutureUtil.runAsyncWithExceptionAlly(asyncTaskExecutor,
                    () -> deleteImageReference(oldImageUrls));
            return;
        }
        // 如果旧图片列表为空，则增加新图片列表的引用
        if (CollectionUtils.isEmpty(oldImageUrls)) {
            CompletableFutureUtil.runAsyncWithExceptionAlly(asyncTaskExecutor,
                    // 将新增图片URL列表保存到Redis中
                    () -> redisService.sAdd(RedisConstant.DB_IMAGE_NAME, newImageUrls.toArray()),
                    // 处理需要增加的图片URL列表
                    () -> addImageReference(newImageUrls));
            return;
        }
        // 检索两个列表的差集，得到需要增加的图片URL列表
        List<String> newDifference = (List<String>) CollectionUtil
                .difference(newImageUrls, oldImageUrls);
        // 检索两个列表的差集，得到需要删除的图片URL列表
        List<String> oldDifference = (List<String>) CollectionUtil
                .difference(oldImageUrls, newImageUrls);
        // 检查需要新增的图片是否存在redis中，如果不在则说明是网络图片直接移除
        newDifference.removeIf(imageUrl -> !redisService.sIsMember(RedisConstant.TEMP_IMAGE_NAME, imageUrl));
        CompletableFutureUtil.runAsyncWithExceptionAlly(asyncTaskExecutor,
                // 将新增图片URL列表保存到Redis中
                () -> redisService.sAdd(RedisConstant.DB_IMAGE_NAME, newDifference.toArray()),
                // 处理需要增加的图片URL列表
                () -> addImageReference(newDifference),
                // 处理需要删除的图片URL列表
                () -> deleteImageReference(oldDifference));
    }
}
