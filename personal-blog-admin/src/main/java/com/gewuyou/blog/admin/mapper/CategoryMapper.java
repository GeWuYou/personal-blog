package com.gewuyou.blog.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gewuyou.blog.common.dto.CategoryAdminDTO;
import com.gewuyou.blog.common.dto.CategoryDTO;
import com.gewuyou.blog.common.model.Category;
import com.gewuyou.blog.common.vo.ConditionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 分类表 Mapper 接口
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

    /**
     * 查询分类列表
     *
     * @return 分类列表
     */
    List<CategoryDTO> listCategoryDTOs();

    /**
     * 查询后台分类列表
     *
     * @param page        分页对象
     * @param conditionVO 条件
     * @return 后台分类列表
     */
    List<CategoryAdminDTO> listCategoryAdminDTOs(Page<CategoryAdminDTO> page, @Param("conditionVO") ConditionVO conditionVO);
}
