package com.gewuyou.blog.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 上传模式枚举
 *
 * @author gewuyou
 * @since 2024-05-06 下午8:20:42
 */
@Getter
@AllArgsConstructor
public enum UploadModeEnum {
    OSS("oss", "ossUploadStrategyImpl"),

    MINIO("minio", "minioUploadStrategyImpl"),

    QINIU("qiniu", "qiNiuUploadStrategyImpl");

    private final String mode;

    private final String strategy;

    public static String getStrategy(String mode) {
        for (UploadModeEnum value : UploadModeEnum.values()) {
            if (value.getMode().equals(mode)) {
                return value.getStrategy();
            }
        }
        return null;
    }
}
