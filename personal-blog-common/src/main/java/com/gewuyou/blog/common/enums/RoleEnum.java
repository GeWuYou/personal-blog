package com.gewuyou.blog.common.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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

    USER(2, "用户", "user"),

    TEST(3, "测试", "test");

    private final Integer roleId;

    private final String name;

    private final String label;
}
