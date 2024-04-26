package com.gewuyou.blog.server.service.impl;

import com.gewuyou.blog.server.entity.ArticleTag;
import com.gewuyou.blog.server.mapper.ArticleTagMapper;
import com.gewuyou.blog.server.service.IArticleTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文章标签中间表 服务实现类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements IArticleTagService {

}
