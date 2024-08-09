package com.gewuyou.blog.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gewuyou.blog.common.model.ImageReference;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
}
