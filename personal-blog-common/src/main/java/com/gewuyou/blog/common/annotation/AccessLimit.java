package com.gewuyou.blog.common.annotation;

import java.lang.annotation.*;

/**
 * 访问限制
 *
 * @author gewuyou
 * @since 2024-05-19 下午4:20:59
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessLimit {
    /**
     * 访问时间间隔
     *
     * @return 时间间隔
     */
    int seconds();

    /**
     * 间隔内最大访问次数
     *
     * @return 最大访问次数
     */
    int maxCount();
}
