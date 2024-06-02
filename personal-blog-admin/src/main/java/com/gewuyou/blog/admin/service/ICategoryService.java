package com.gewuyou.blog.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gewuyou.blog.common.dto.CategoryAdminDTO;
import com.gewuyou.blog.common.dto.CategoryOptionDTO;
import com.gewuyou.blog.common.dto.PageResultDTO;
import com.gewuyou.blog.common.model.Category;
import com.gewuyou.blog.common.vo.ArticleVO;
import com.gewuyou.blog.common.vo.CategoryVO;
import com.gewuyou.blog.common.vo.ConditionVO;

import java.util.List;

/**
 * <p>
 * 分类表 服务类
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
public interface ICategoryService extends IService<Category> {


    /**
     * 根据文章VO保存分类
     *
     * @param articleVO 文章VO
     */
    void saveCategoryByArticleVO(ArticleVO articleVO);


    /**
     * 查询后台分类列表
     *
     * @param conditionVO 条件
     * @return 后台分类列表
     */
    PageResultDTO<CategoryAdminDTO> listCategoryAdminDTOs(ConditionVO conditionVO);

    /**
     * 查询后台分类选项列表
     *
     * @param conditionVO 条件
     * @return 后台分类选项列表
     */
    List<CategoryOptionDTO> listCategoryOptionDTOsBySearch(ConditionVO conditionVO);

    /**
     * 删除分类
     *
     * @param categoryIds 分类ID列表
     */
    void deleteCategories(List<Integer> categoryIds);

    /**
     * 保存或更新分类
     *
     * @param categoryVO 分类VO
     */
    void saveOrUpdateCategory(CategoryVO categoryVO);
}
