package com.gewuyou.blog.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文件路径枚举
 *
 * @author gewuyou
 * @since 2024-05-05 下午9:17:59
 */
@Getter
@AllArgsConstructor
public enum FilePathEnum {
    AVATAR("blog/avatar/", "头像路径"),

    ARTICLE("blog/articles/", "文章图片路径"),

    VOICE("blog/voice/", "音频路径"),

    PHOTO("blog/photos/", "相册路径"),

    CONFIG("blog/config/", "配置图片路径"),

    TALK("blog/talks/", "配置图片路径"),

    MD("blog/markdown/", "md文件路径");

    private final String path;

    private final String desc;
}
