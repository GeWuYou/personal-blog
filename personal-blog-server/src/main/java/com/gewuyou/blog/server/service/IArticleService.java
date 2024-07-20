package com.gewuyou.blog.server.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gewuyou.blog.common.dto.*;
import com.gewuyou.blog.common.model.Article;
import com.gewuyou.blog.common.vo.ArticleAccessPasswordVO;
import com.gewuyou.blog.common.vo.ConditionVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 文章表 服务类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
public interface IArticleService extends IService<Article> {

    /**
     * 查询未删除的文章数量
     *
     * @return 文章数量
     */
    Long selectCountNotDeleted();

    /**
     * 查询文章排行榜
     *
     * @param articleMap 文章id和评分映射
     * @return 文章排行榜
     */
    List<ArticleRankDTO> listArticleRank(Map<Long, Double> articleMap);


    /**
     * 获取置顶和推荐的文章
     *
     * @return 置顶和推荐的文章
     */
    TopAndFeaturedArticlesDTO listTopAndFeaturedArticles();

    /**
     * 查询文章
     *
     * @return 文章列表
     */
    PageResultDTO<ArticleCardDTO> listArticleCardDTOs();

    /**
     * 根据分类id查询文章列表
     *
     * @param categoryId 分类id
     * @return 文章列表卡片DTO
     */
    PageResultDTO<ArticleCardDTO> listArticleCardDTOsByCategoryId(Long categoryId);

    /**
     * 根据标签id查询文章
     *
     * @param articleId 标签id
     * @return 文章DTO
     */
    ArticleDTO getArticleDTOById(Long articleId);

    /**
     * 验证文章访问密码
     *
     * @param articleAccessPasswordVO 文章访问VO
     */
    void verifyArticleAccessPassword(ArticleAccessPasswordVO articleAccessPasswordVO);

    /**
     * 根据标签id查询文章列表
     *
     * @param tagId 标签id
     * @return 文章列表卡片DTO
     */
    PageResultDTO<ArticleCardDTO> listArticleCardDTOsByTagId(Long tagId);

    /**
     * 查询文章归档
     *
     * @return 文章归档DTO
     */
    PageResultDTO<ArchiveDTO> listArchiveDTOs();

    /**
     * 根据搜索条件搜索文章
     *
     * @param condition 搜索条件
     * @return 文章列表
     */
    List<EsArticleDTO> listArticlesBySearch(ConditionVO condition);

    /**
     * 查询文章统计信息
     *
     * @return 文章统计信息
     */
    List<ArticleStatisticsDTO> listArticleStatistics();
}
