package com.gewuyou.blog.admin.service;

import com.gewuyou.blog.common.vo.ArticleVO;
import com.gewuyou.blog.common.vo.DeleteVO;

/**
 * 文章事务服务接口
 *
 * @author gewuyou
 * @since 2024-05-12 下午10:20:44
 */
public interface IArticleTransactionalService {
    /**
     * 保存或更新文章
     *
     * @param articleVO 文章VO
     */
    void saveOrUpdateArticle(ArticleVO articleVO);

    /**
     * 更新文章删除状态
     *
     * @param deleteVO 删除VO
     */
    void updateArticleDelete(DeleteVO deleteVO);
}
