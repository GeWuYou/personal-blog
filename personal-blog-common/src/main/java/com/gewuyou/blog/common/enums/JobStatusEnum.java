package com.gewuyou.blog.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 作业状态枚举
 *
 * @author gewuyou
 * @since 2024-05-29 下午4:02:02
 */
@AllArgsConstructor
@Getter
public enum JobStatusEnum {
    NORMAL(Byte.valueOf("1")),

    PAUSE(Byte.valueOf("0"));

    private final Byte value;
}
