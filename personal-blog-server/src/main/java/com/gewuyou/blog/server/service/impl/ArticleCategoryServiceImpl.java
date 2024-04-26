package com.gewuyou.blog.server.service.impl;

import com.gewuyou.blog.server.entity.ArticleCategory;
import com.gewuyou.blog.server.mapper.ArticleCategoryMapper;
import com.gewuyou.blog.server.service.IArticleCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文章分类中间表 服务实现类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
@Service
public class ArticleCategoryServiceImpl extends ServiceImpl<ArticleCategoryMapper, ArticleCategory> implements IArticleCategoryService {

}
