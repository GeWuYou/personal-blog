package com.gewuyou.blog.common.utils;

import java.util.Optional;

/**
 * Redis工具类
 *
 * @author gewuyou
 * @since 2024-07-20 上午10:22:09
 */
public class RedisUtil {
    public static Long getLongValue(Object key) {
        return Optional.of(((Number) key).longValue()).orElse(0L);
    }

    public static Long getLongValue(Object key, Long defaultValue) {
        return Optional.of(((Number) key).longValue()).orElse(defaultValue);
    }
}
