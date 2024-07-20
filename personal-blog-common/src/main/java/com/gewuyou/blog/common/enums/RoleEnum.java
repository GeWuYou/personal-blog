package com.gewuyou.blog.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 角色枚举
 *
 * @author gewuyou
 * @since 2024-04-22 下午3:59:31
 */
@Getter
@AllArgsConstructor
public enum RoleEnum {
    ADMIN(1, "管理员", "admin"),

    USER(19, "用户", "user"),

    TEST(3, "测试", "test");

    private final Integer roleId;

    private final String name;

    private final String label;
}
