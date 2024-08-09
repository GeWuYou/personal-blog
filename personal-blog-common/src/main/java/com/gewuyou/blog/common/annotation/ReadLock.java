package com.gewuyou.blog.common.annotation;

import jakarta.validation.constraints.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 读锁
 *
 * @author gewuyou
 * @since 2024-08-08 19:39:06
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ReadLock {
    @NotNull
    String value();

    long leaseTime() default -1L;

    TimeUnit unit() default TimeUnit.SECONDS;
}
