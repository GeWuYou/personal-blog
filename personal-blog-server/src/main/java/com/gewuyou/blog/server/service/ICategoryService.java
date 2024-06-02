package com.gewuyou.blog.server.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gewuyou.blog.common.dto.CategoryDTO;
import com.gewuyou.blog.common.model.Category;

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

    Long selectCount();


    /**
     * 查询分类列表
     *
     * @return 分类列表
     */
    List<CategoryDTO> listCategoryDTOs();

}
