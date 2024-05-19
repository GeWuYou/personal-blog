package com.gewuyou.blog.common.constant;

/**
 * 消息常量
 *
 * @author gewuyou
 * @since 2024-04-17 下午10:41:51
 */
public class MessageConstant {
    private MessageConstant() {
    }

    /**
     * 无效的token请求
     */
    public static final String INVALID_TOKEN_REQUEST = "无效token请求!";
    /**
     * token为空
     */
    public static final String TOKEN_IS_EMPTY = "token为空!";
    /**
     * token无效
     */
    public static final String TOKEN_IS_INVALID = "token无效!";
    /**
     * token不合法
     */
    public static final String TOKEN_IS_NOT_LEGITIMATE = "token不合法";
    /**
     * token已过期
     */
    public static final String TOKEN_HAS_EXPIRED = "token已过期!";
    /**
     * token已刷新
     */
    public static final String TOKEN_HAS_BEEN_REFRESHED = "token已刷新!";
    /**
     * 服务器内部错误，请联系管理员
     */
    public static final String SERVER_INTERNAL_ERROR = "服务器内部错误，请联系管理员!";
    /**
     * 当前用户未绑定角色
     */
    public static final String ROLE_IS_NOT_BOUND = "当前用户未绑定角色!";

    /**
     * 已放行
     */
    public static final String RELEASED = "已放行!";
    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESSFUL = "登录成功!";
    /**
     * 用户名或邮箱不能为空
     */
    public static final String USER_NAME_OR_EMAIL_ADDRESS_CANNOT_BE_EMPTY = "用户名或邮箱不能为空!";
    /**
     * 用户名或密码错误
     */
    public static final String USERNAME_OR_PASSWORD_IS_INCORRECT = "用户名或密码错误!";
    /**
     * 该用户名已存在
     */
    public static final String USER_NAME_ALREADY_EXISTS = "该用户名已存在!";
    /**
     * 密码不能为空
     */
    public static final String PASSWORD_CANNOT_BE_EMPTY = "密码不能为空!";
    /**
     * 重置密码成功
     */
    public static final String PASSWORD_RESET_WAS_SUCCESSFUL = "重置密码成功!";

    /**
     * 邮箱不能为空
     */
    public static final String MAIL_CANNOT_BE_EMPTY = "邮箱不能为空!";
    /**
     * 无效邮箱地址
     */
    public static final String INVALID_EMAIL_ADDRESS = "无效邮箱地址，没有存在该邮件地址的账户!";
    /**
     * 邮件发送错误
     */
    public static final String MESSAGE_WAS_SENT_INCORRECTLY = "邮件发送失败,请确认邮箱是否有效或联系管理员。";
    /**
     * 邮箱已注册
     */
    public static final String EMAIL_IS_REGISTERED = "该邮箱已被注册";
    /**
     * 当前邮件发送请求过于频繁，请稍后后再试
     */
    public static final String MESSAGES_ARE_SENT_FREQUENTLY = "当前邮件发送请求过于频繁，请稍后后再试!";
    /**
     * 请先完成邮箱验证
     */
    public static final String PLEASE_COMPLETE_EMAIL_VERIFICATION_FIRST = "请先完成邮箱验证!";
    /**
     * 验证码不能为空
     */
    public static final String VERIFICATION_CODE_CANNOT_BE_EMPTY = "验证码不能为空!";
    /**
     * 验证码错误
     */
    public static final String VERIFICATION_CODE_ERROR = " 验证码错误!";
    /**
     * 验证码已失效
     */
    public static final String VERIFICATION_CODE_LAPSE = "验证码已失效!";
    /**
     * 请先获取验证码
     */
    public static final String VERIFICATION_CODE_IS_REQUIRED = "请先获取验证码!";
    /**
     * 验证码已发送
     */
    public static final String VERIFICATION_CODE_HAS_BEEN_SENT = "验证码已发送,请注意查收!";
    /**
     * 验证成功
     */
    public static final String VALIDATION_SUCCEEDED = "验证成功!";
    /**
     * 注册成功
     */
    public static final String REGISTRATION_SUCCESSFUL = "注册成功!";
    /**
     * 操作成功
     */
    public static final String OPERATION_SUCCEEDED = "操作成功!";
    /**
     * 操作失败
     */
    public static final String OPERATION_FAILED = "操作失败!";

    /**
     * 启用成功
     */
    public static final String ENABLED_SUCCESSFULLY = "启用成功!";
    /**
     * 禁用成功
     */
    public static final String DISABLE_SUCCESSFULLY = "禁用成功!";
    /**
     * 分配成功
     */
    public static final String ASSIGNMENT_SUCCEEDED = "分配成功!";

    /**
     * 文章标题不能为空
     */
    public static final String ARTICLE_CONTENT_NOT_EMPTY = "文章内容不能为空!";

    public static final String ARTICLE_TITLE_NOT_EMPTY = "文章标题不能为空!";
    public static final String ARTICLE_CATEGORY_NOT_EMPTY = "文章分类不能为空!";
    public static final String ARTICLE_TAG_NOT_EMPTY = "文章标签不能为空!";
    public static final String CATEGORY_NAME_CANNOT_BE_EMPTY = "分类名不能为空!";
}
