package com.gewuyou.blog.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gewuyou.blog.common.model.ImageReference;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 图像参考映射器
 *
 * @author gewuyou
 * @since 2024-08-09 22:51:08
 */
@Mapper
public interface ImageReferenceMapper extends BaseMapper<ImageReference> {
    /**
     * 根据图片URL查询图片引用
     *
     * @param imageUrl 图片URL
     * @return 图片引用
     */
    ImageReference selectByImageUrl(@Param("imageUrl") String imageUrl);

    /**
     * 根据图片URL列表查询图片引用
     *
     * @param imageUrls 图片URL列表
     * @return 图片引用列表
     */
    List<ImageReference> selectByImageUrls(@Param("imageUrls") List<String> imageUrls);

    /**
     * 批量更新图片引用
     *
     * @param imageReferences 图片引用列表
     */
    void updateBatchById(@Param("imageReferences") List<ImageReference> imageReferences);

    /**
     * 批量插入图片引用
     *
     * @param imageReferences 图片引用列表
     */
    void insertBatch(@Param("imageReferences") List<ImageReference> imageReferences);
}
