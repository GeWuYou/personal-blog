package com.gewuyou.blog.admin.controller;

import com.gewuyou.blog.admin.security.service.JwtService;
import com.gewuyou.blog.admin.service.IAdminService;
import com.gewuyou.blog.common.annotation.GlobalLogging;
import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import com.gewuyou.blog.common.dto.*;
import com.gewuyou.blog.common.entity.Result;
import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.enums.TokenType;
import com.gewuyou.blog.common.exception.GlobalException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.gewuyou.blog.common.constant.MessageConstant.USER_NAME_OR_EMAIL_ADDRESS_CANNOT_BE_EMPTY;
import static com.gewuyou.blog.common.constant.RegularConstant.USERNAME_REGULARITY;

/**
 * 管理控制器
 *
 * @author gewuyou
 * @since 2024-04-19 下午7:14:26
 */
@Tag(name = "管理控制器", description = "管理控制器")
@RestController
@RequestMapping(InterfacePermissionConstant.BASE_URL + "/admin")
public class AdminController {

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final HttpSession httpSession;

    private final IAdminService adminService;

    private static final String SESSION_TAGS = "reset-password";


    @Autowired
    public AdminController(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            HttpSession httpSession,
            IAdminService adminService

    ) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.httpSession = httpSession;
        this.adminService = adminService;
    }

    /**
     * 登录接口
     *
     * @param loginDto 登录DTO
     * @param response 响应
     * @return 登录结果
     */
    @Operation(summary = "登录接口", description = "登录接口")
    @PostMapping("/login")
    @GlobalLogging(logResult = false)
    public Result<String> login(@Validated @RequestBody LoginDTO loginDto, HttpServletResponse response) {
        // 创建一个认证令牌
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(),
                loginDto.getPassword()
        );
        // 认证
        Authentication authentication = authenticationManager.authenticate(authToken);
        // 获取用户信息
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        // 登录成功
        if (authentication.isAuthenticated()) {
            // 生成刷新token和访问token
            String refreshToken = jwtService.generateToken(userDetails, TokenType.RefreshToken);
            String accessToken = jwtService.generateToken(userDetails, TokenType.AccessToken);
            // 设置cookie
            response.addHeader("Set-Cookie", "refreshToken=" + refreshToken + ";path=/;HttpOnly");
            response.addHeader("Set-Cookie", "accessToken=" + accessToken + ";path=/;HttpOnly");
            return Result.success(ResponseInformation.LOGIN_SUCCESS);
        } else {
            return Result.failure(ResponseInformation.AUTHENTICATION_FAILED);
        }
    }

    /**
     * 发送邮件注册接口
     *
     * @param registerEmailDTO 注册邮箱DTO
     * @return 发送结果
     */
    @Operation(summary = "发送邮件注册接口", description = "发送邮件注册接口")
    @PostMapping("/send-register-email")
    @GlobalLogging(logResult = false)
    public Result<String> sendRegisterEmail(@Validated @RequestBody RegisterEmailDTO registerEmailDTO) {
        try {
            if (adminService.sendEmail(registerEmailDTO.getEmail(), httpSession.getId(), false)) {
                return Result.success(ResponseInformation.VERIFICATION_CODE_HAS_BEEN_SENT);
            } else {
                return Result.failure(ResponseInformation.SEND_REGISTER_EMAIL_FAILED);
            }
        } catch (GlobalException e) {
            return Result.failure(e.getResponseInformation());
        }
    }

    /**
     * 发送重置密码邮件接口
     *
     * @param registerEmailDTO 注册邮箱DTO
     * @return 发送结果
     */
    @Operation(summary = "发送重置密码邮件接口", description = "发送重置密码邮件接口")
    @PostMapping("/validate-reset-email")
    @GlobalLogging(logResult = false)
    public Result<String> validateResetEmail(@Validated @RequestBody RegisterEmailDTO registerEmailDTO) {
        try {
            if (adminService.sendEmail(registerEmailDTO.getEmail(), httpSession.getId(), true)) {
                return Result.success(ResponseInformation.VERIFICATION_CODE_HAS_BEEN_SENT);
            } else {
                return Result.failure(ResponseInformation.SEND_REGISTER_EMAIL_FAILED);
            }
        } catch (GlobalException e) {
            return Result.failure(e.getResponseInformation());
        }
    }


    /**
     * 注册接口
     *
     * @param registerDTO 注册DTO
     * @return 注册结果
     */
    @Operation(summary = "注册接口", description = "注册接口")
    @PostMapping("/register")
    @GlobalLogging(logResult = false)
    public Result<String> register(@Validated @RequestBody RegisterDTO registerDTO) {
        try {
            if (adminService.verifyEmailAndRegister(registerDTO, httpSession.getId())) {
                return Result.success(ResponseInformation.REGISTER_SUCCESS);
            } else {
                return Result.failure(ResponseInformation.VERIFICATION_CODE_ERROR);
            }
        } catch (GlobalException e) {
            return Result.failure(e.getResponseInformation());
        }
    }

    /**
     * 重置密码接口
     *
     * @param startResetPasswordDTO 开始重置密码DTO
     * @return 重置密码结果
     */
    @Operation(summary = "重置密码接口", description = "重置密码接口")
    @PostMapping("/start-reset")
    @GlobalLogging(logResult = false)
    public Result<String> startResetPassword(@Validated @RequestBody StartResetPasswordDTO startResetPasswordDTO) {
        try {
            if (adminService.verifyCode(startResetPasswordDTO.getEmail(), startResetPasswordDTO.getVerifyCode(), httpSession.getId(), true)) {
                // 校验通过，向会话中写入标记
                httpSession.setAttribute(SESSION_TAGS, startResetPasswordDTO.getEmail());
                return Result.success(ResponseInformation.REGISTER_SUCCESS);
            } else {
                return Result.failure(ResponseInformation.VERIFICATION_CODE_ERROR);
            }
        } catch (GlobalException e) {
            return Result.failure(e.getResponseInformation());
        }
    }

    /**
     * 重置密码接口
     *
     * @param doResetPasswordDTO 重置密码DTO
     * @return 重置密码结果
     */
    @Operation(summary = "重置密码接口", description = "重置密码接口")
    @PostMapping("/do-reset")
    @GlobalLogging(logResult = false)
    public Result<String> doResetPassword(@Validated @RequestBody DoResetPasswordDTO doResetPasswordDTO) {
        // 获取会话中的标记
        String email = (String) httpSession.getAttribute(SESSION_TAGS);
        if (email == null) {
            return Result.failure(ResponseInformation.PLEASE_COMPLETE_EMAIL_VERIFICATION_FIRST);
        }
        try {
            if (adminService.resetPassword(email, doResetPasswordDTO.getPassword())) {
                // 重置密码成功，清除会话中的标记
                httpSession.removeAttribute(SESSION_TAGS);
                return Result.success(ResponseInformation.RESET_PASSWORD_SUCCESS);
            } else {
                return Result.failure(ResponseInformation.SERVER_ERROR);
            }
        } catch (GlobalException e) {
            return Result.failure(e.getResponseInformation());
        }
    }

    /**
     * 获取用户信息接口
     *
     * @param username 用户名
     * @return 用户信息
     */
    @Parameter(name = "username", description = "用户名", in = ParameterIn.QUERY, required = true)
    @Operation(summary = "获取用户信息接口", description = "获取用户信息接口")
    @GetMapping("/check-username")
    @GlobalLogging(logResult = false)
    public Result<String> checkUserName(
            @Validated
            @RequestParam("username")
            @Pattern(regexp = USERNAME_REGULARITY)
            @NotEmpty(message = USER_NAME_OR_EMAIL_ADDRESS_CANNOT_BE_EMPTY)
            @NotBlank(message = USER_NAME_OR_EMAIL_ADDRESS_CANNOT_BE_EMPTY)
            String username) {
        if (adminService.checkUserName(username)) {
            return Result.failure(ResponseInformation.USER_HAS_EXISTED);
        } else {
            return Result.success();
        }
    }
}
