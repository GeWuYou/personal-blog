package com.gewuyou.blog.admin.controller;

import com.gewuyou.blog.admin.service.ICategoryService;
import com.gewuyou.blog.common.annotation.OperationLogging;
import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import com.gewuyou.blog.common.dto.CategoryAdminDTO;
import com.gewuyou.blog.common.dto.CategoryOptionDTO;
import com.gewuyou.blog.common.dto.PageResultDTO;
import com.gewuyou.blog.common.entity.Result;
import com.gewuyou.blog.common.enums.OperationType;
import com.gewuyou.blog.common.vo.CategoryVO;
import com.gewuyou.blog.common.vo.ConditionVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类前端控制器
 *
 * @author gewuyou
 * @since 2024-06-01 下午10:47:07
 */
@Tag(name = "分类前端控制器", description = "分类前端控制器")
@RestController
@RequestMapping(InterfacePermissionConstant.ADMIN_BASE_URL + "/category")
public class CategoryController {

    private final ICategoryService categoryService;

    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * 查看后台分类列表
     *
     * @param conditionVO 条件
     * @return 分类列表
     */
    @Operation(summary = "查看后台分类列表", description = "查看后台分类列表")
    @GetMapping("/list")
    public Result<PageResultDTO<CategoryAdminDTO>> listCategoriesAdmin(ConditionVO conditionVO) {
        return Result.success(categoryService.listCategoryAdminDTOs(conditionVO));
    }

    /**
     * 搜索后台文章分类
     *
     * @param conditionVO 条件
     * @return 分类列表
     */
    @Operation(summary = "搜索后台文章分类", description = "搜索后台文章分类")
    @GetMapping("/search")
    public Result<List<CategoryOptionDTO>> listCategoriesAdminBySearch(ConditionVO conditionVO) {
        return Result.success(categoryService.listCategoryOptionDTOsBySearch(conditionVO));
    }

    /**
     * 删除分类
     *
     * @param categoryIds 分类id列表
     * @return 成功或失败
     */
    @Operation(summary = "删除分类", description = "删除分类")
    @OperationLogging(type = OperationType.DELETE)
    @DeleteMapping
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
    @Operation(summary = "保存或更新后台分类", description = "保存或更新后台分类")
    @OperationLogging(type = OperationType.SAVE_OR_UPDATE)
    @PostMapping
    public Result<?> saveOrUpdateCategory(@Validated @RequestBody CategoryVO categoryVO) {
        categoryService.saveOrUpdateCategory(categoryVO);
        return Result.success();
    }

}
