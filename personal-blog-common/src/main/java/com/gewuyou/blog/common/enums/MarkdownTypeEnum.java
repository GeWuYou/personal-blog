package com.gewuyou.blog.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Markdown 类型枚举
 *
 * @author gewuyou
 * @since 2024-05-13 下午7:54:49
 */
@Getter
@AllArgsConstructor
public enum MarkdownTypeEnum {
    NORMAL("", "normalArticleImportStrategyImpl");

    private final String type;

    private final String strategy;

    public static String getMarkdownType(String name) {
        if (name == null) {
            return NORMAL.getStrategy();
        }
        for (MarkdownTypeEnum value : MarkdownTypeEnum.values()) {
            if (value.getType().equalsIgnoreCase(name)) {
                return value.getStrategy();
            }
        }
        return null;
    }
}
