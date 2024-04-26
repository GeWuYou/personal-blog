package com.gewuyou.blog.common.enums;

import lombok.Getter;

/**
 * 响应信息
 *
 * @author gewuyou
 * @since 2024-04-16 下午6:12:35
 */
@Getter
public enum ResponseInformation {
    /**
     * 成功
     */
    SUCCESS(200, "成功"),
    FAIL(1000, "失败"),
    /**
     * 权限错误
     */
    INSUFFICIENT_PERMISSIONS(403, "需要完全身份验证才能访问此路径或资源!"),
    LOGIN_SUCCESS(200, "登录成功"),
    REGISTER_SUCCESS(200, "注册成功"),
    LOGOUT_SUCCESS(200, "注销成功"),
    RESET_PASSWORD_SUCCESS(200, "密码重置成功"),
    CHANGE_PASSWORD_SUCCESS(200, "密码修改成功"),
    EMAIL_VERIFICATION_SUCCESS(200, "邮箱验证成功"),

    VERIFICATION_CODE_HAS_BEEN_SENT(200, "验证码已发送,请注意查收!"),
    FAILED(400, "请求失败"),
    VERIFICATION_CODE_ERROR(400, "验证码错误"),
    VERIFY_CODE_EXPIRED(400, "验证码已过期"),
    INVALID_TOKEN(401, "无效的token"),
    INVALID_TOKEN_REQUEST(400, "无效的token请求"),
    TOKEN_EXPIRED(401, "token已过期"),
    AUTHENTICATION_FAILED(401, "身份验证失败"),
    PLEASE_COMPLETE_EMAIL_VERIFICATION_FIRST(401, "请先完成邮箱验证"),
    TOO_MANY_REQUESTS(429, "请求过于频繁,请稍后再试"),


    NOT_FOUND(404, "未找到"),
    SERVER_ERROR(500, " 服务器内部出错 "),
    ENCRYPTION_FAILED(500, "加密失败"),
    LOG_BUILD_FAILED(500, "日志构建失败"),

    /**
     * 错误参数
     */
    PARAM_IS_INVALID(1001, "参数无效"),
    PARAM_IS_BLANK(1002, "参为空"),
    PARAM_TYPE_ERROR(1003, "参数类型错误"),
    PARAM_NOT_COMPLETE(1004, "参数缺失"),
    /**
     * 用户错误
     */
    USER_NOT_LOGIN_IN(2001, "用户未登录"),
    USERNAME_LOGIN_ERROR(2002, "用户名不存在或者密码错误"),
    USER_EMAIL_LOGIN_ERROR(2002, "邮箱不存在或者密码错误"),
    SEND_REGISTER_EMAIL_FAILED(2002, "邮件发送失败,请确认邮箱是否有效或联系管理员)"),
    USER_ACCOUNT_FORBIDDEN(2003, "账户被禁用"),
    USER_NOT_EXISTS(2004, "用户不存在"),
    USER_HAS_EXISTED(2005, "用户已存在"),
    USERNAME_OR_EMAIL_IS_NULL(2006, "用户名或邮箱不能为空"),
    USER_EMAIL_HAS_BEEN_REGISTERED(2007, "邮箱已被注册"),
    USER_EMAIL_NOT_REGISTERED(2008, "邮箱未注册"),

    /**
     * 服务错误
     */
    ERR_GET_LOCAL_IP_FAILED(3001, "获取本地IP失败"),


    // 工具错误
    JSON_PARSE_ERROR(500, "JSON解析失败"),
    JSON_SERIALIZE_ERROR(500, "JSON序列化失败"),
    JSON_DESERIALIZE_ERROR(500, "JSON反序列化失败"),
    OBJECT_COPY_FAILED(500, "对象拷贝失败"),
    IO_EXCEPTION(500, "IO异常");


    /**
     * 代码
     */
    private final Integer code;
    /**
     * 信息
     */
    private final String message;

    ResponseInformation(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
