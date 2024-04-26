package com.gewuyou.blog.server.service.impl;

import com.gewuyou.blog.server.entity.ArticleViewStatistic;
import com.gewuyou.blog.server.mapper.ArticleViewStatisticMapper;
import com.gewuyou.blog.server.service.IArticleViewStatisticService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文章访问统计表 服务实现类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
@Service
public class ArticleViewStatisticServiceImpl extends ServiceImpl<ArticleViewStatisticMapper, ArticleViewStatistic> implements IArticleViewStatisticService {

}
