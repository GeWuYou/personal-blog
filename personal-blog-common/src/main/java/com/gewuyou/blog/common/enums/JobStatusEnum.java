package com.gewuyou.blog.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.gewuyou.blog.common.constant.CommonConstant.FALSE;
import static com.gewuyou.blog.common.constant.CommonConstant.TRUE;

/**
 * 作业状态枚举
 *
 * @author gewuyou
 * @since 2024-05-29 下午4:02:02
 */
@AllArgsConstructor
@Getter
public enum JobStatusEnum {
    NORMAL(TRUE),

    PAUSE(FALSE);

    private final Byte value;
}
