package com.gewuyou.blog.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文章状态枚举
 *
 * @author gewuyou
 * @since 2024-05-06 下午10:47:23
 */
@Getter
@AllArgsConstructor
public enum ArticleStatusEnum {
    PUBLIC(Byte.valueOf("1"), "公开"),

    PRIVATE(Byte.valueOf("2"), "私密"),

    DRAFT(Byte.valueOf("3"), "草稿");

    private final Byte status;

    private final String desc;
}
