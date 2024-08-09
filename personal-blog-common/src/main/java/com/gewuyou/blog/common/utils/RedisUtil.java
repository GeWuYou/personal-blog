package com.gewuyou.blog.common.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * Redis工具类
 *
 * @author gewuyou
 * @since 2024-07-20 上午10:22:09
 */
@Slf4j
public class RedisUtil {
    public static Long getLongValue(Object key) {
        return getLongValue(key, 0L);
    }

    public static Long getLongValue(Object key, Long defaultValue) {
        if (key instanceof Integer value) {
            return value.longValue();
        } else if (key instanceof Long value) {
            return value;
        } else if (key instanceof String value) {
            try {
                return Long.valueOf(value);
            } catch (NumberFormatException e) {
                log.error("redis key value is not a number, key:{}, value:{}", key, value);
                return defaultValue;
            }
        } else {
            log.error("unknown redis key type, key:{}, value:{}", key, key);
            return defaultValue;
        }
    }
}
