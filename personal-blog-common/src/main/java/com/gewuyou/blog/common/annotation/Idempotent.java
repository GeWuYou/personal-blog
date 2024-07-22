package com.gewuyou.blog.common.annotation;

import java.lang.annotation.*;

/**
 * 幂等
 *
 * @author gewuyou
 * @since 2024-07-19 下午11:59:30
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Idempotent {
    /**
     * 幂等请求间隔时间，单位秒
     *
     * @return 延迟时间
     */
    long delay() default 5;
}
