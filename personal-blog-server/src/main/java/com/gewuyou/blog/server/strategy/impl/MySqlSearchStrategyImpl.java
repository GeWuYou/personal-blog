package com.gewuyou.blog.server.strategy.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gewuyou.blog.common.dto.EsArticleDTO;
import com.gewuyou.blog.common.model.Article;
import com.gewuyou.blog.common.strategy.interfaces.SearchStrategy;
import com.gewuyou.blog.server.mapper.ArticleMapper;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.gewuyou.blog.common.constant.CommonConstant.*;
import static com.gewuyou.blog.common.enums.ArticleStatusEnum.PUBLIC;

/**
 * MySQL 搜索策略 实现
 *
 * @author gewuyou
 * @since 2024-05-18 下午4:46:44
 */
@Service("mysqlSearchStrategyImpl")
public class MySqlSearchStrategyImpl implements SearchStrategy {

    private final ArticleMapper articleMapper;

    @Autowired
    public MySqlSearchStrategyImpl(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    @Override
    public List<EsArticleDTO> searchArticle(String keywords) {
        if (StringUtils.isBlank(keywords)) {
            return new ArrayList<>();
        }
        List<Article> articles = articleMapper.selectList(
                new LambdaQueryWrapper<Article>()
                        .eq(Article::getIsDelete, FALSE)
                        .eq(Article::getStatus, PUBLIC.getStatus())
                        .and(
                                i ->
                                        i.like(Article::getArticleTitle, keywords)
                                                .or()
                                                .like(Article::getArticleContent, keywords)

                        )
        );
        return articles
                .stream()
                .map(
                        article -> {
                            boolean isLowercase = true;
                            // 文章内容高亮
                            String articleContent;
                            int contentIndex = article.getArticleContent().indexOf(keywords.toLowerCase());
                            if (contentIndex == -1) {
                                contentIndex = article.getArticleContent().indexOf(keywords.toUpperCase());
                                if (contentIndex != -1) {
                                    isLowercase = false;
                                }
                            }
                            if (contentIndex != -1) {
                                int preIndex = contentIndex > 15 ? contentIndex - 15 : 0;
                                String preText = article.getArticleContent().substring(preIndex, contentIndex);
                                int last = contentIndex + keywords.length();
                                int postLength = article.getArticleContent().length() - last;
                                int postIndex = postLength > 35 ? last + 35 : last + postLength;
                                String postText = article.getArticleContent().substring(contentIndex, postIndex);
                                if (isLowercase) {
                                    articleContent = (preText + postText).replaceAll(keywords.toLowerCase(),
                                            PRE_TAG + keywords.toLowerCase() + POST_TAG);
                                } else {
                                    articleContent = (preText + postText).replaceAll(keywords.toUpperCase(),
                                            PRE_TAG + keywords.toUpperCase() + POST_TAG);
                                }
                            } else {
                                return null;
                            }
                            isLowercase = true;
                            int titleIndex = article.getArticleTitle().indexOf(keywords.toLowerCase());
                            if (titleIndex == -1) {
                                titleIndex = article.getArticleTitle().indexOf(keywords.toUpperCase());
                                if (titleIndex == -1) {
                                    isLowercase = false;
                                }
                            }
                            // 文章标题高亮
                            String articleTitle = getArticleTitle(keywords, article, isLowercase);

                            return EsArticleDTO
                                    .builder()
                                    .id(article.getId())
                                    .articleTitle(articleTitle)
                                    .articleContent(articleContent)
                                    .build();
                        }).filter(Objects::nonNull)
                .toList();
    }

    private static @NotNull String getArticleTitle(String keywords, Article article, boolean isLowercase) {
        String articleTitle;
        if (isLowercase) {
            articleTitle = article.getArticleTitle().replaceAll(keywords.toLowerCase(),
                    PRE_TAG + keywords.toLowerCase() + POST_TAG);
        } else {
            articleTitle = article.getArticleTitle().replaceAll(keywords.toUpperCase(),
                    PRE_TAG + keywords.toUpperCase() + POST_TAG);
        }
        return articleTitle;
    }
}
