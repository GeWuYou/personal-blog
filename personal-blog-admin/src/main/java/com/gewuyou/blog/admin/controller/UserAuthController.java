package com.gewuyou.blog.admin.controller;

import com.gewuyou.blog.admin.service.IUserAuthService;
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
import jakarta.servlet.http.HttpSession;
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
 * <p>
 * 用户认证信息表 前端控制器
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Tag(name = "<p> 用户认证信息表 前端控制器 </p>", description = "<p> 用户认证信息表 前端控制器 </p>")
@RestController
@RequestMapping(InterfacePermissionConstant.ADMIN_BASE_URL + "/user")
public class UserAuthController {
    private final IUserAuthService userAuthService;

    private final HttpSession httpSession;

    private static final String SESSION_TAGS = "reset-password";

    @Autowired
    public UserAuthController(
            IUserAuthService userAuthService,
            HttpSession httpSession
    ) {
        this.userAuthService = userAuthService;
        this.httpSession = httpSession;
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
    public Result<UserInfoDTO> login(@Validated @RequestBody LoginVO loginVO) {
        return Result.success(ResponseInformation.LOGIN_SUCCESS, userAuthService.usernameOrEmailPasswordLogin(loginVO));
    }

    /**
     * 发送邮件注册接口
     *
     * @param registerEmailVO 注册邮箱DTO
     * @return 发送结果
     */
    @Operation(summary = "发送邮件注册接口", description = "发送邮件注册接口")
    @PostMapping("/register/email")
    public Result<String> sendRegisterEmail(@Validated @RequestBody RegisterEmailVO registerEmailVO) {
        if (userAuthService.sendEmail(registerEmailVO.getEmail(), httpSession.getId(), false)) {
            return Result.success(ResponseInformation.VERIFICATION_CODE_HAS_BEEN_SENT);
        } else {
            return Result.failure(ResponseInformation.SEND_REGISTER_EMAIL_FAILED);
        }
    }

    /**
     * 发送重置密码邮件接口
     *
     * @param registerEmailVO 注册邮箱DTO
     * @return 发送结果
     */
    @Operation(summary = "发送重置密码邮件接口", description = "发送重置密码邮件接口")
    @PostMapping("/reset-password/email")
    public Result<String> sendResetPasswordEmail(@Validated @RequestBody RegisterEmailVO registerEmailVO) {
        if (userAuthService.sendEmail(registerEmailVO.getEmail(), httpSession.getId(), true)) {
            return Result.success(ResponseInformation.VERIFICATION_CODE_HAS_BEEN_SENT);
        } else {
            return Result.failure(ResponseInformation.SEND_REGISTER_EMAIL_FAILED);
        }
    }

    /**
     * 退出登录接口
     *
     * @return 退出登录结果
     */
    @PostMapping("/logout")
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
    public Result<String> register(@Validated @RequestBody RegisterVO registerVO) {
        if (userAuthService.verifyEmailAndRegister(registerVO, httpSession.getId())) {
            return Result.success(ResponseInformation.REGISTER_SUCCESS);
        } else {
            return Result.failure(ResponseInformation.VERIFICATION_CODE_ERROR);
        }
    }

    /**
     * 重置密码接口
     *
     * @param startResetPasswordVO 开始重置密码DTO
     * @return 重置密码结果
     */
    @Operation(summary = "重置密码接口", description = "重置密码接口")
    @PostMapping("/reset-password/verify-code")
    public Result<String> startResetPassword(@Validated @RequestBody StartResetPasswordVO startResetPasswordVO) {
        if (userAuthService.verifyCode(startResetPasswordVO.getEmail(), startResetPasswordVO.getVerifyCode(), httpSession.getId(), true)) {
            // 校验通过，向会话中写入标记
            httpSession.setAttribute(SESSION_TAGS, startResetPasswordVO.getEmail());
            return Result.success(ResponseInformation.REGISTER_SUCCESS);
        } else {
            return Result.failure(ResponseInformation.VERIFICATION_CODE_ERROR);
        }
    }

    /**
     * 重置密码接口
     *
     * @param doResetPasswordVO 重置密码DTO
     * @return 重置密码结果
     */
    @Operation(summary = "重置密码接口", description = "重置密码接口")
    @PostMapping("/reset-password")
    @OperationLogging(logResult = false, logParams = false, type = OperationType.UPDATE)
    public Result<String> doResetPassword(@Validated @RequestBody DoResetPasswordVO doResetPasswordVO) {
        // 获取会话中的标记
        String email = (String) httpSession.getAttribute(SESSION_TAGS);
        if (email == null) {
            return Result.failure(ResponseInformation.PLEASE_COMPLETE_EMAIL_VERIFICATION_FIRST);
        }
        if (userAuthService.resetPassword(email, doResetPasswordVO.getPassword())) {
            // 重置密码成功，清除会话中的标记
            httpSession.removeAttribute(SESSION_TAGS);
            return Result.success(ResponseInformation.RESET_PASSWORD_SUCCESS);
        } else {
            return Result.failure(ResponseInformation.SERVER_ERROR);
        }
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
}
