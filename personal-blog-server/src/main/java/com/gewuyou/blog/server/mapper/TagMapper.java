package com.gewuyou.blog.server.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gewuyou.blog.common.model.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 标签表 Mapper 接口
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

    /**
     * 根据文章ID查询标签名称列表
     *
     * @param articleId 文章ID
     * @return 标签名称列表
     */
    List<String> listTagNamesByArticleId(@Param("articleId") Long articleId);
}
