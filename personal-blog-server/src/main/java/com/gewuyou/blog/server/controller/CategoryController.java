package com.gewuyou.blog.server.controller;

import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import com.gewuyou.blog.common.dto.CategoryDTO;
import com.gewuyou.blog.common.entity.Result;
import com.gewuyou.blog.server.service.ICategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * 分类表 前端控制器
 *
 *
 * @author gewuyou
 * @since 2024-04-23
 */
@Tag(name = "分类表 前端控制器", description = "分类表 前端控制器")
@RestController
@RequestMapping(InterfacePermissionConstant.SERVER_BASE_URL + "/category")
public class CategoryController {

    private final ICategoryService categoryService;

    @Autowired
    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * 获取所有分类
     *
     * @return 分类列表
     */
    @Operation(summary = "获取所有分类", description = "获取所有分类")
    @GetMapping("/list")
    public Result<List<CategoryDTO>> listCategories() {
        return Result.success(categoryService.listCategoryDTOs());
    }

    /**
     * 获取分类数量
     *
     * @return 分类数量
     */
    @RequestMapping("/count")
    public Long selectCount() {
        return categoryService.selectCount();
    }
}
