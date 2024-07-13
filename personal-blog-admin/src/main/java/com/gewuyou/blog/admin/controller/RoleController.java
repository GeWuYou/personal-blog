package com.gewuyou.blog.admin.controller;

import com.gewuyou.blog.admin.service.IRoleService;
import com.gewuyou.blog.common.annotation.OperationLogging;
import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import com.gewuyou.blog.common.dto.PageResultDTO;
import com.gewuyou.blog.common.dto.RoleDTO;
import com.gewuyou.blog.common.dto.UserRoleDTO;
import com.gewuyou.blog.common.entity.Result;
import com.gewuyou.blog.common.enums.OperationType;
import com.gewuyou.blog.common.vo.ConditionVO;
import com.gewuyou.blog.common.vo.RoleVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Tag(name = "<p> 角色表 前端控制器 </p>", description = "<p> 角色表 前端控制器 </p>")
@RestController
@RequestMapping(InterfacePermissionConstant.ADMIN_BASE_URL + "/role")
public class RoleController {
    private final IRoleService roleService;


    @Autowired
    public RoleController(IRoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * 查询用户角色选项
     *
     * @return 用户角色选项
     */
    @Operation(summary = "查询用户角色选项", description = "查询用户角色选项")
    @GetMapping("/user/list")
    public Result<List<UserRoleDTO>> listUserRoles() {
        return Result.success(roleService.listUserRoleDTOs());
    }


    /**
     * 查询角色列表
     *
     * @param conditionVO 条件
     * @return 角色列表
     */
    @Operation(summary = "查询角色列表", description = "查询角色列表")
    @GetMapping("/list")
    public Result<PageResultDTO<RoleDTO>> listRoles(ConditionVO conditionVO) {
        return Result.success(roleService.listRoleDTOs(conditionVO));
    }

    /**
     * 保存或更新角色
     *
     * @param roleVO 角色VO
     * @return 成功或失败
     */
    @Operation(summary = "保存或更新角色", description = "保存或更新角色")
    @OperationLogging(type = OperationType.SAVE_OR_UPDATE)
    @PostMapping
    public Result<?> saveOrUpdateRole(@RequestBody @Valid RoleVO roleVO) {
        roleService.saveOrUpdateRole(roleVO);
        return Result.success();
    }

    /**
     * 删除角色
     *
     * @param roleIdList 角色ID列表
     * @return 成功或失败
     */
    @Operation(summary = "删除角色", description = "删除角色")
    @OperationLogging(type = OperationType.DELETE)
    @DeleteMapping
    public Result<?> deleteRoles(@RequestBody List<Integer> roleIdList) {
        roleService.deleteRoles(roleIdList);
        return Result.success();
    }
}
