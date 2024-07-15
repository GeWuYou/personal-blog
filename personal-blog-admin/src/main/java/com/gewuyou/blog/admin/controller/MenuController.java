package com.gewuyou.blog.admin.controller;

import com.gewuyou.blog.admin.service.IMenuService;
import com.gewuyou.blog.common.annotation.OperationLogging;
import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import com.gewuyou.blog.common.dto.LabelOptionDTO;
import com.gewuyou.blog.common.dto.MenuDTO;
import com.gewuyou.blog.common.dto.UserMenuDTO;
import com.gewuyou.blog.common.entity.Result;
import com.gewuyou.blog.common.enums.OperationType;
import com.gewuyou.blog.common.vo.ConditionVO;
import com.gewuyou.blog.common.vo.IsHiddenVO;
import com.gewuyou.blog.common.vo.MenuVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * 菜单表 前端控制器
 *
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Tag(name = "菜单表 前端控制器", description = "菜单表 前端控制器")
@RestController
@RequestMapping(InterfacePermissionConstant.ADMIN_BASE_URL + "/menu")
public class MenuController {

    private final IMenuService menuService;

    @Autowired
    public MenuController(
            IMenuService menuService
    ) {
        this.menuService = menuService;
    }

    /**
     * 查看菜单列表
     *
     * @param conditionVO 条件
     * @return 菜单列表传输类
     */
    @Operation(summary = "查看菜单列表", description = "查看菜单列表")
    @GetMapping
    public Result<List<MenuDTO>> listMenus(ConditionVO conditionVO) {
        return Result.success(menuService.listMenus(conditionVO));
    }

    /**
     * 保存或更新菜单
     *
     * @param menuVO 菜单视图对象
     * @return 结果
     */
    @Operation(summary = "保存或更新菜单", description = "保存或更新菜单")
    @OperationLogging(logResult = false, logParams = false, type = OperationType.SAVE_OR_UPDATE)
    @PostMapping
    public Result<?> saveOrUpdateMenu(@Valid @RequestBody MenuVO menuVO) {
        menuService.saveOrUpdateMenu(menuVO);
        return Result.success();
    }

    /**
     * 修改目录是否隐藏
     *
     * @param isHiddenVO 目录是否隐藏视图对象
     * @return 结果
     */
    @Operation(summary = "修改目录是否隐藏", description = "修改目录是否隐藏")
    @OperationLogging(logResult = false, logParams = false, type = OperationType.UPDATE)
    @PutMapping("/isHidden")
    public Result<?> updateMenuIsHidden(@RequestBody IsHiddenVO isHiddenVO) {
        menuService.updateMenuIsHidden(isHiddenVO);
        return Result.success();
    }

    /**
     * 删除菜单
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    @Parameter(name = "menuId", description = "菜单ID", in = ParameterIn.PATH, required = true)
    @Operation(summary = "删除菜单", description = "删除菜单")
    @OperationLogging(logResult = false, type = OperationType.DELETE)
    @DeleteMapping("/{menuId}")
    public Result<?> deleteMenu(@PathVariable("menuId") Integer menuId) {
        menuService.deleteMenu(menuId);
        return Result.success();
    }

    /**
     * 获取用户菜单列表
     *
     * @return 用户菜单列表
     */
    @Operation(summary = "获取用户菜单列表", description = "获取用户菜单列表")
    @GetMapping("/user")
    public Result<List<UserMenuDTO>> listUserMenus() {
        return Result.success(menuService.listUserMenus());
    }

    /**
     * 获取角色菜单选项
     *
     * @return 角色菜单选项
     */
    @Operation(summary = "获取角色菜单选项", description = "获取角色菜单选项")
    @GetMapping("/role")
    public Result<List<LabelOptionDTO>> listRoleMenuOptions() {
        return Result.success(menuService.listRoleMenuOptions());
    }

}
