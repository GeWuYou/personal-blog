package com.gewuyou.blog.server.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gewuyou.blog.common.model.Category;
import com.gewuyou.blog.common.vo.ArticleVO;

/**
 * <p>
 * 分类表 服务类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
public interface ICategoryService extends IService<Category> {

    Long selectCount();

    /**
     * 根据文章VO保存分类
     *
     * @param articleVO 文章VO
     * @return 分类
     */
    Category saveCategoryByArticleVO(ArticleVO articleVO);
}
