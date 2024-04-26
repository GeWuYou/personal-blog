package com.gewuyou.blog.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 登录类型枚举
 *
 * @author gewuyou
 * @since 2024-04-25 下午9:50:14
 */
@Getter
@AllArgsConstructor
public enum LoginTypeEnum {
    USERNAME_OR_EMAIL(1, "用户名或邮箱密码登录", "usernameOrEmailPasswordLoginStrategy"),
    EMAIL(2, "邮箱验证码登录", "emailVerificationCodeLoginStrategy"),
    MOBILE(3, "手机号验证码登录", "mobileOrVerificationCodeLoginStrategy"),
    WECHAT(4, "微信登录", "wechatLoginStrategy"),
    WEIBO(5, "微博登录", "weiboLoginStrategy"),
    QQ(6, "QQ登录", "qqLoginStrategy"),
    ;


    private final Integer type;

    private final String desc;

    private final String strategy;
}
