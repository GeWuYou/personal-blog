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
    USERNAME_OR_EMAIL(Byte.valueOf("1"), "用户名或邮箱密码登录", "usernameOrEmailPasswordLoginStrategy"),
    EMAIL(Byte.valueOf("2"), "邮箱验证码登录", "emailVerificationCodeLoginStrategy"),
    MOBILE(Byte.valueOf("3"), "手机号验证码登录", "mobileOrVerificationCodeLoginStrategy"),
    WECHAT(Byte.valueOf("4"), "微信登录", "wechatLoginStrategy"),
    WEIBO(Byte.valueOf("5"), "微博登录", "weiboLoginStrategy"),
    QQ(Byte.valueOf("6"), "QQ登录", "qqLoginStrategy"),
    ;


    private final Byte type;

    private final String desc;

    private final String strategy;
}
