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
    ARTICLE_ACCESS_PASSWORD_ERROR(400, "文章访问密码错误"),
    NON_EMPTY_CATEGORICAL_DELETION_REQUEST(400, "该分类下存在文章，不能删除"),
    NON_EMPTY_TAG_DELETION_REQUEST(400, "该标签下存在文章，不能删除"),
    NON_EMPTY_ROLE_DELETION_REQUEST(400, "该资源下存在角色关联，不能删除"),
    CATEGORY_NAME_ALREADY_EXISTS(400, "分类名称已存在"),
    TAG_NAME_ALREADY_EXISTS(400, "标签名称已存在"),
    ROLE_EXIST(400, "角色已存在"),
    ROLE_IN_USE(400, "角色下存在用户，不能删除"),
    ALBUM_NAME_EXIST(400, "相册名称已存在"),
    ALBUM_NOT_EXISTS(400, "相册不存在"),
    CATEGORY_NOT_EXISTS(400, "分类不存在"),
    VERIFY_CODE_EXPIRED(400, "验证码已过期"),
    INVALID_TOKEN(401, "无效的token"),
    INVALID_TOKEN_REQUEST(400, "无效的token请求"),
    PARAMETER_VALIDATION_EXCEPTION(400, "参数校验异常"),
    TOKEN_EXPIRED(401, "token已过期"),
    AUTHENTICATION_FAILED(401, "身份验证失败"),
    PLEASE_COMPLETE_EMAIL_VERIFICATION_FIRST(401, "请先完成邮箱验证"),
    TOO_MANY_REQUESTS(429, "请求过于频繁,请稍后再试"),
    ARTICLE_NOT_ACCESS(403, "文章不可访问"),


    NOT_FOUND(404, "未找到"),
    SERVER_ERROR(500, " 服务器内部出错 "),
    ENCRYPTION_FAILED(500, "加密失败"),
    LOG_BUILD_FAILED(500, "日志构建失败"),
    GET_ARTICLE_ERROR(500, "获取文章失败"),
    ARTICLE_NOT_EXIST(500, "文章不存在"),
    FAILED_TO_LOAD_PUBLIC_KEY(500, "加载公钥失败"),
    FAILED_TO_LOAD_PRIVATE_KEY(500, "加载私钥失败"),
    FAILED_TO_INITIALIZE_THE_KEY_PAIR(500, "无法初始化加密服务的密钥对"),
    ERROR_OCCURRED_WHILE_ENCRYPTING_THE_DATA(500, "加密数据时发生错误"),
    FAILED_TO_GENERATE_THE_DIGITAL_SIGNATURE(500, "生成数字签名时发生错误"),
    FAILED_TO_VERIFY_THE_DIGITAL_SIGNATURE(500, "验证数字签名时发生错误"),
    ASYNC_EXCEPTION(500, "异步任务异常"),
    CREATE_TASK_FAILED(500, "创建任务失败"),
    DELETE_TASK_FAILED(500, "删除任务失败"),

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
     * 菜单错误
     */
    MENU_HAS_BEEN_ASSOCIATED_WITH_A_ROLE(3001, "菜单已被角色关联，不能修改或删除"),
    /**
     * 服务错误
     */
    ERR_GET_LOCAL_IP_FAILED(4001, "获取本地IP失败"),
    FAILED_TO_UPDATE_WEBSITE_CONFIGURATION(4002, "更新网站配置失败"),
    IMPORT_ARTICLE_FAILED(4003, "导入文章失败"),
    GET_COUNT_ERROR(4004, "获取计数失败"),
    // 工具错误
    JSON_PARSE_ERROR(500, "JSON解析失败"),
    JSON_SERIALIZE_ERROR(500, "JSON序列化失败"),
    JSON_DESERIALIZE_ERROR(500, "JSON反序列化失败"),
    RESPONSE_INFORMATION(500, "解析token失败"),
    OBJECT_COPY_FAILED(500, "对象拷贝失败"),
    FILE_UPLOAD_FAILED(500, "文件上传失败"),
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
