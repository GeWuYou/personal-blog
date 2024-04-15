package com.gewuyou.blog.common.annotation;

import com.gewuyou.blog.common.enums.LoggingLevel;

import java.lang.annotation.*;

/**
 * 全局日志注解
 *
 * @author gewuyou
 * @since 2024-04-13 下午4:40:35
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.METHOD})
public @interface GlobalLogging {
    /**
     * 设定默认的日志级别
     * @return Info
     */
    LoggingLevel level() default LoggingLevel.INFO;

    /**
     * 是否记录参数
     * @return true
     */
    boolean logParams() default true;

    /**
     * 是否记录返回结果
     * @return true
     */
    boolean logResult() default true;

    /**
     * 是否记录异常
     * @return true
     */
    boolean logException() default true;
}
