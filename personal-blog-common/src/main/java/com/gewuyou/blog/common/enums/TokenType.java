package com.gewuyou.blog.common.enums;

/**
 * token类型枚举
 *
 * @author gewuyou
 * @since 2024-04-20 下午12:53:06
 */
public enum TokenType {
    AccessToken("access-token"),
    RefreshToken("refresh-token");

    private final String value;

    public String getValue() {
        return value;
    }

    TokenType(String value) {
        this.value = value;
    }
}
