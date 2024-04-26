package com.gewuyou.blog.server.mapper;

import com.gewuyou.blog.server.entity.ArticleLikeStatistic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 文章点赞统计表 Mapper 接口
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
@Mapper
public interface ArticleLikeStatisticMapper extends BaseMapper<ArticleLikeStatistic> {

}
