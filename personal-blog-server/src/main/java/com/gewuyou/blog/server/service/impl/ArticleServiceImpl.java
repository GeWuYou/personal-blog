package com.gewuyou.blog.server.service.impl;

import com.gewuyou.blog.server.entity.Article;
import com.gewuyou.blog.server.mapper.ArticleMapper;
import com.gewuyou.blog.server.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文章表 服务实现类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

}
