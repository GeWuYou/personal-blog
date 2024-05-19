package com.gewuyou.blog.server.controller;

import com.gewuyou.blog.common.annotation.OperationLogging;
import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import com.gewuyou.blog.common.dto.CategoryAdminDTO;
import com.gewuyou.blog.common.dto.CategoryDTO;
import com.gewuyou.blog.common.dto.CategoryOptionDTO;
import com.gewuyou.blog.common.dto.PageResultDTO;
import com.gewuyou.blog.common.entity.Result;
import com.gewuyou.blog.common.enums.OperationType;
import com.gewuyou.blog.common.vo.CategoryVO;
import com.gewuyou.blog.common.vo.ConditionVO;
import com.gewuyou.blog.server.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 分类表 前端控制器
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
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
    @GetMapping("/list")
    public Result<List<CategoryDTO>> listCategories() {
        return Result.success(categoryService.listCategoryDTOs());
    }

    /**
     * 查看后台分类列表
     *
     * @param conditionVO 条件
     * @return 分类列表
     */
    @GetMapping("/admin/list")
    public Result<PageResultDTO<CategoryAdminDTO>> listCategoriesAdmin(ConditionVO conditionVO) {
        return Result.success(categoryService.listCategoryAdminDTOs(conditionVO));
    }

    /**
     * 搜索后台文章分类
     *
     * @param conditionVO 条件
     * @return 分类列表
     */
    @GetMapping("/admin/search")
    public Result<List<CategoryOptionDTO>> listCategoriesAdminBySearch(ConditionVO conditionVO) {
        return Result.success(categoryService.listCategoryOptionDTOsBySearch(conditionVO));
    }

    /**
     * 删除分类
     *
     * @param categoryIds 分类id列表
     * @return 成功或失败
     */
    @OperationLogging(type = OperationType.DELETE)
    @DeleteMapping("/admin/list")
    public Result<?> deleteCategories(@RequestBody List<Integer> categoryIds) {
        categoryService.deleteCategories(categoryIds);
        return Result.success();
    }

    /**
     * 保存或更新后台分类
     *
     * @param categoryVO 分类信息
     * @return 成功或失败
     */
    @OperationLogging(type = OperationType.SAVE_OR_UPDATE)
    @PostMapping("/admin")
    public Result<?> saveOrUpdateCategory(@Validated @RequestBody CategoryVO categoryVO) {
        categoryService.saveOrUpdateCategory(categoryVO);
        return Result.success();
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
