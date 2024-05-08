package com.gewuyou.blog.server.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gewuyou.blog.common.dto.ArticleRankDTO;
import com.gewuyou.blog.common.model.Article;
import com.gewuyou.blog.common.vo.ArticleVO;

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
    List<ArticleRankDTO> listArticleRank(Map<Object, Double> articleMap);

    /**
     * 保存或更新文章
     *
     * @param articleVO 文章VO
     */
    void saveOrUpdateArticle(ArticleVO articleVO);
}
