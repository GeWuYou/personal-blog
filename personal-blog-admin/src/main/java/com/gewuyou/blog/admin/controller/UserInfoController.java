package com.gewuyou.blog.admin.controller;

import com.gewuyou.blog.admin.service.IUserInfoService;
import com.gewuyou.blog.common.annotation.OperationLogging;
import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import com.gewuyou.blog.common.dto.PageResultDTO;
import com.gewuyou.blog.common.dto.UserOnlineDTO;
import com.gewuyou.blog.common.entity.Result;
import com.gewuyou.blog.common.enums.OperationType;
import com.gewuyou.blog.common.vo.ConditionVO;
import com.gewuyou.blog.common.vo.UserDisableVO;
import com.gewuyou.blog.common.vo.UserRoleVO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户信息控制器
 *
 * @author gewuyou
 * @since 2024-07-04 下午9:20:42
 */
@RestController
@RequestMapping(InterfacePermissionConstant.ADMIN_BASE_URL + "/user-info")
public class UserInfoController {

    private final IUserInfoService userInfoService;

    @Autowired
    public UserInfoController(IUserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    /**
     * 修改用户角色
     *
     * @param userRoleVO 用户角色DTO
     * @return 修改结果
     */
    @OperationLogging(type = OperationType.UPDATE)
    @PutMapping("/role")
    public Result<?> updateUserRole(@Valid @RequestBody UserRoleVO userRoleVO) {
        userInfoService.updateUserRole(userRoleVO);
        return Result.success();
    }

    /**
     * 修改用户禁用状态
     *
     * @param userDisableVO 用户禁用DTO
     * @return 修改结果
     */
    @OperationLogging(type = OperationType.UPDATE)
    @PutMapping("/disable")
    public Result<?> updateUserDisable(@Valid @RequestBody UserDisableVO userDisableVO) {
        userInfoService.updateUserDisable(userDisableVO);
        return Result.success();
    }

    /**
     * 查看在线用户
     *
     * @param conditionVO 条件
     * @return 在线用户列表
     */
    @GetMapping("/online")
    public Result<PageResultDTO<UserOnlineDTO>> listOnlineUsers(ConditionVO conditionVO) {
        return Result.success(userInfoService.listOnlineUsers(conditionVO));
    }

    /**
     * 下线用户
     *
     * @param userInfoId 用户ID
     * @return 下线结果
     */
    @OperationLogging(type = OperationType.DELETE)
    @DeleteMapping("/{userInfoId}/online")
    public Result<?> removeOnlineUser(@PathVariable("userInfoId") Long userInfoId) {
        userInfoService.removeOnlineUser(userInfoId);
        return Result.success();
    }

}
