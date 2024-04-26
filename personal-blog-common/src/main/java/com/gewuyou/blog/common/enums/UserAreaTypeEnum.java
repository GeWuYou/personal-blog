package com.gewuyou.blog.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户区类型枚举
 *
 * @author gewuyou
 * @since 2024-04-22 下午9:34:13
 */
@Getter
@AllArgsConstructor
public enum UserAreaTypeEnum {
    USER_TYPE(1, "用户"),
    VISITOR_TYPE(2, "游客");

    private final Integer type;
    private final String desc;

    // 构建类型到枚举实例的映射
    private static final Map<Integer, UserAreaTypeEnum> TYPE_MAP = new HashMap<>();

    static {
        for (UserAreaTypeEnum enumValue : UserAreaTypeEnum.values()) {
            TYPE_MAP.put(enumValue.getType(), enumValue);
        }
    }

    public static UserAreaTypeEnum getUserAreaType(Integer type) {
        return TYPE_MAP.get(type);
    }
}
