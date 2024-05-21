package com.gewuyou.blog.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 评论类型枚举
 *
 * @author gewuyou
 * @since 2024-05-19 下午7:24:34
 */
@Getter
@AllArgsConstructor
public enum CommentTypeEnum {
    ARTICLE(Byte.valueOf("1"), "文章", "/articles/"),

    MESSAGE(Byte.valueOf("2"), "留言", "/message/"),

    ABOUT(Byte.valueOf("3"), "关于我", "/about/"),

    LINK(Byte.valueOf("4"), "友链", "/friends/"),

    TALK(Byte.valueOf("5"), "说说", "/talks/");

    private final Byte type;

    private final String desc;

    private final String path;

    public static String getCommentPath(Byte type) {
        for (CommentTypeEnum value : CommentTypeEnum.values()) {
            if (value.getType().equals(type)) {
                return value.getPath();
            }
        }
        return null;
    }

    public static CommentTypeEnum getCommentEnum(Byte type) {
        for (CommentTypeEnum value : CommentTypeEnum.values()) {
            if (value.getType().equals(type)) {
                return value;
            }
        }
        return null;
    }
}
