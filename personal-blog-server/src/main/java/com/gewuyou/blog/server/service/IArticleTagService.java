package com.gewuyou.blog.server.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gewuyou.blog.common.model.ArticleTag;
import com.gewuyou.blog.common.vo.ArticleVO;

/**
 * <p>
 * 文章标签中间表 服务类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
public interface IArticleTagService extends IService<ArticleTag> {

    /**
     * 根据文章VO与文章ID保存文章标签中间表数
     *
     * @param articleVO 文章VO
     * @param articleId 文章ID
     */
    void saveByArticleVOAndId(ArticleVO articleVO, Long articleId);
}
