package com.gewuyou.blog.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gewuyou.blog.common.dto.ArticleAdminDTO;
import com.gewuyou.blog.common.dto.ArticleAdminViewDTO;
import com.gewuyou.blog.common.dto.PageResultDTO;
import com.gewuyou.blog.common.model.Article;
import com.gewuyou.blog.common.vo.ArticleTopFeaturedVO;
import com.gewuyou.blog.common.vo.ConditionVO;

import java.util.List;
import java.util.concurrent.CompletableFuture;

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
     * 查询后台文章列表
     *
     * @param conditionVO 条件VO
     * @return 文章列表DTO
     */
    PageResultDTO<ArticleAdminDTO> listArticlesAdminDTOs(ConditionVO conditionVO);

    /**
     * 更新文章置顶和推荐
     *
     * @param articleTopFeaturedVO 文章置顶和推荐VO
     */
    void updateArticleTopAndFeatured(ArticleTopFeaturedVO articleTopFeaturedVO);


    /**
     * 根据文章id获取后台文章
     *
     * @param articleId 文章id
     * @return 后台文章DTO
     */
    ArticleAdminViewDTO getArticleAdminViewDTOById(Long articleId);

    /**
     * 批量导出文章
     *
     * @param articleIds 文章id列表
     * @return 导出文件名集合
     */
    CompletableFuture<List<String>> exportArticles(List<Integer> articleIds);

}
