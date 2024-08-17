package com.gewuyou.blog.admin.controller;

import com.gewuyou.blog.admin.service.IUserAuthService;
import com.gewuyou.blog.common.annotation.AccessLimit;
import com.gewuyou.blog.common.annotation.Idempotent;
import com.gewuyou.blog.common.annotation.OperationLogging;
import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import com.gewuyou.blog.common.dto.PageResultDTO;
import com.gewuyou.blog.common.dto.UserAdminDTO;
import com.gewuyou.blog.common.dto.UserAreaDTO;
import com.gewuyou.blog.common.dto.UserInfoDTO;
import com.gewuyou.blog.common.entity.Result;
import com.gewuyou.blog.common.enums.OperationType;
import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.vo.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.gewuyou.blog.common.constant.MessageConstant.USER_NAME_OR_EMAIL_ADDRESS_CANNOT_BE_EMPTY;
import static com.gewuyou.blog.common.constant.RegularConstant.USERNAME_REGULARITY;

/**
 * 用户认证信息表 前端控制器
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Tag(name = "用户认证信息表 前端控制器", description = "用户认证信息表 前端控制器")
@RestController
@RequestMapping(InterfacePermissionConstant.ADMIN_BASE_URL + "/user")
public class UserAuthController {
    private final IUserAuthService userAuthService;

    @Autowired
    public UserAuthController(
            IUserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    /**
     * 获取用户区域分布
     *
     * @param conditionVO 条件
     * @return 用户区域分布
     */
    @Operation(summary = "获取用户区域分布", description = "获取用户区域分布")
    @GetMapping("/area")
    public Result<List<UserAreaDTO>> listUserAreas(ConditionVO conditionVO) {
        return Result.success(userAuthService.listUserAreas(conditionVO));
    }

    /**
     * 获取后台用户列表
     *
     * @param conditionVO 条件
     * @return 后台用户列表
     */
    @Operation(summary = "获取后台用户列表", description = "获取后台用户列表")
    @GetMapping("/list")
    public Result<PageResultDTO<UserAdminDTO>> listUsers(ConditionVO conditionVO) {
        return Result.success(userAuthService.listUsers(conditionVO));
    }

    /**
     * 用户名或邮箱密码登录接口
     *
     * @param loginVO 登录DTO
     * @return 登录结果
     */
    @Operation(summary = "登录接口", description = "登录接口")
    @PostMapping("/login")
    @OperationLogging(logResult = false)
    @Idempotent
    public Result<UserInfoDTO> login(@Validated @RequestBody LoginVO loginVO) {
        return Result.success(ResponseInformation.LOGIN_SUCCESS, userAuthService.usernameOrEmailPasswordLogin(loginVO));
    }

    /**
     * 发送验证码
     *
     * @param TargetEmailVO 目标邮箱DTO
     * @return 发送结果
     */
    @Operation(summary = "发送验证码", description = "发送验证码")
    @AccessLimit(seconds = 60, maxCount = 1)
    @PostMapping("/code")
    @Idempotent
    public Result<String> sendCodeToEmail(@Validated @RequestBody TargetEmailVO TargetEmailVO) {
        userAuthService.sendCodeToEmail(TargetEmailVO.getEmail());
        return Result.success(ResponseInformation.VERIFICATION_CODE_HAS_BEEN_SENT);
    }

    /**
     * 退出登录接口
     *
     * @return 退出登录结果
     */
    @Operation(summary = "退出登录接口", description = "退出登录接口")
    @PostMapping("/logout")
    @Idempotent
    public Result<String> logout() {
        return Result.success(userAuthService.logout());
    }


    /**
     * 注册接口
     *
     * @param registerVO 注册DTO
     * @return 注册结果
     */
    @Operation(summary = "注册接口", description = "注册接口")
    @PostMapping("/register")
    @Idempotent
    public Result<String> register(@Validated @RequestBody RegisterVO registerVO) {
        userAuthService.verifyEmailAndRegister(registerVO);
        return Result.success(ResponseInformation.REGISTER_SUCCESS);
    }

    /**
     * 管理员重置密码接口
     *
     * @param adminPasswordVO 重置密码DTO
     * @return 重置密码结果
     */
    @Operation(summary = "重置密码接口", description = "重置密码接口")
    @PostMapping("/admin-password")
    @OperationLogging(logResult = false, logParams = false, type = OperationType.UPDATE)
    @Idempotent
    public Result<String> doResetPassword(@Validated @RequestBody AdminPasswordVO adminPasswordVO) {
        userAuthService.updateAdminPassword(adminPasswordVO);
        return Result.success();
    }

    /**
     * 修改密码
     *
     * @param userVO 用户视图信息
     * @return 修改结果
     */
    @Operation(summary = "修改密码", description = "修改密码")
    @OperationLogging(type = OperationType.UPDATE)
    @PutMapping("/user-password")
    public Result<?> updatePassword(@Valid @RequestBody UserVO userVO) {
        userAuthService.updatePassword(userVO);
        return Result.success();
    }

    /**
     * 获取用户信息接口
     *
     * @param username 用户名
     * @return 用户信息
     */
    @Parameter(name = "username", description = "用户名", in = ParameterIn.QUERY, required = true)
    @Operation(summary = "检查用户是否存在接口", description = "检查用户是否存在接口")
    @GetMapping("/check-username")
    public Result<String> checkUserName(
            @Validated
            @RequestParam("username")
            @Pattern(regexp = USERNAME_REGULARITY)
            @NotEmpty(message = USER_NAME_OR_EMAIL_ADDRESS_CANNOT_BE_EMPTY)
            @NotBlank(message = USER_NAME_OR_EMAIL_ADDRESS_CANNOT_BE_EMPTY)
            String username) {
        if (userAuthService.checkUserName(username)) {
            return Result.failure(ResponseInformation.USER_HAS_EXISTED);
        } else {
            return Result.success();
        }
    }

    /**
     * qq登录接口
     *
     * @param qqLoginVO qq登录DTO
     * @return 登录结果
     */
    @Operation(summary = "qq登录接口", description = "qq登录接口")
    @PostMapping("/oauth/qq")
    @Idempotent
    public Result<UserInfoDTO> qqLogin(@Valid @RequestBody QQLoginVO qqLoginVO) {
        return Result.success(userAuthService.qqLogin(qqLoginVO));
    }
}
