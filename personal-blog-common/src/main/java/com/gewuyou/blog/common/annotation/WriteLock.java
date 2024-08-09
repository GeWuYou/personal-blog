package com.gewuyou.blog.common.annotation;

import jakarta.validation.constraints.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 写锁
 *
 * @author gewuyou
 * @since 2024-08-08 19:40:39
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface WriteLock {
    @NotNull
    String value();

    long leaseTime() default -1L;

    TimeUnit unit() default TimeUnit.SECONDS;
}
