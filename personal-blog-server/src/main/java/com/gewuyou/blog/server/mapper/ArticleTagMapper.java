package com.gewuyou.blog.server.mapper;

import com.gewuyou.blog.server.entity.ArticleTag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 文章标签中间表 Mapper 接口
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
@Mapper
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {

}
