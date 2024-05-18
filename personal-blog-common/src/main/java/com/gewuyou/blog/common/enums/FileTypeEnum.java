package com.gewuyou.blog.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文件类型枚举
 *
 * @author gewuyou
 * @since 2024-05-13 下午8:24:54
 */
@Getter
@AllArgsConstructor
public enum FileTypeEnum {
    JPG(".jpg", "jpg文件"),

    PNG(".png", "png文件"),

    JPEG(".jpeg", "jpeg文件"),

    WAV(".wav", "wav文件"),

    MD(".md", "markdown文件"),

    TXT(".txt", "txt文件");

    public static FileTypeEnum getFileExt(String extName) {
        for (FileTypeEnum fileTypeEnum : FileTypeEnum.values()) {
            if (fileTypeEnum.getTypeName().equals(extName)) {
                return fileTypeEnum;
            }
        }
        return null;
    }

    private final String typeName;

    private final String desc;
}
