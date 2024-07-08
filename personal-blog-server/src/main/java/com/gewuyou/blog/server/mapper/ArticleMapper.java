package com.gewuyou.blog.server.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gewuyou.blog.common.dto.ArticleCardDTO;
import com.gewuyou.blog.common.dto.ArticleDTO;
import com.gewuyou.blog.common.dto.ArticleStatisticsDTO;
import com.gewuyou.blog.common.model.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 文章表 Mapper 接口
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 获取首页置顶和推荐的文章列表
     *
     * @return 文章卡片列表DTO
     */
    List<ArticleCardDTO> listTopAndFeaturedArticles();

    /**
     * 分页查询文章列表
     *
     * @param page 分页对象
     * @return 文章卡片列表DTO
     */
    List<ArticleCardDTO> listArticleCardDTOs(Page<ArticleCardDTO> page);

    /**
     * 根据分类ID分页查询文章列表
     *
     * @param page       分页对象
     * @param capacityId 分类ID
     * @return 文章卡片列表DTO
     */
    List<ArticleCardDTO> listArticleCardDTOsByCategoryId(Page<ArticleCardDTO> page, @Param("id") Long capacityId);

    /**
     * 根据文章ID查询文章卡片DTO
     *
     * @param articleId 文章id
     * @return 文章卡片DTO
     */
    ArticleDTO getArticleCardDTOById(@Param("articleId") Long articleId);

    /**
     * 根据文章ID查询上一篇文章卡片DTO
     *
     * @param articleId 文章id
     * @return 上一篇文章卡片DTO
     */
    ArticleCardDTO getPreArticleCardDTOById(@Param("articleId") Long articleId);

    /**
     * 根据文章ID查询下一篇文章卡片DTO
     *
     * @param articleId 文章id
     * @return 下一篇文章卡片DTO
     */
    ArticleCardDTO getNextArticleCardDTOById(@Param("articleId") Long articleId);

    /**
     * 获取最后一篇文章卡片DTO
     *
     * @return 最后一篇文章卡片DTO
     */
    ArticleCardDTO getLastArticleCardDTO();

    /**
     * 获取第一篇文章卡片DTO
     *
     * @return 第一篇文章卡片DTO
     */
    ArticleCardDTO getFirstArticleCardDTO();


    /**
     * 根据标签ID分页查询文章列表
     *
     * @param page  分页对象
     * @param tagId 标签ID
     * @return 文章卡片列表DTO
     */
    List<ArticleCardDTO> listArticleCardDTOsByTagId(Page<ArticleCardDTO> page, @Param("tagId") Long tagId);

    /**
     * 获取文章统计信息
     *
     * @return 文章统计信息列表
     */
    List<ArticleStatisticsDTO> listArticleStatistics();
}
