package com.gewuyou.blog.common.utils;

import org.springframework.scheduling.support.CronExpression;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;

/**
 * Cron工具类
 *
 * @author gewuyou
 * @since 2024-05-29 下午12:19:03
 */
public class CronUtil {
    private CronUtil() {
    }

    /**
     * 判断cron表达式是否有效
     *
     * @param cronExpression cron表达式
     * @return true：有效；false：无效
     */
    public static boolean isValid(String cronExpression) {
        return CronExpression.isValidExpression(cronExpression);
    }

    /**
     * 获取cron表达式的无效原因
     *
     * @param cronExpression cron表达式
     * @return 无效原因
     */
    public static String getInvalidMessage(String cronExpression) {
        try {
            CronExpression.parse(cronExpression);
            return null;
        } catch (Exception pe) {
            return pe.getMessage();
        }
    }

    /**
     * 获取下一次执行时间
     *
     * @param cronExpression cron表达式
     * @return 下一次执行时间
     */
    public static Date getNextExecution(String cronExpression) {
        try {
            CronExpression expression = CronExpression.parse(cronExpression);
            ZonedDateTime next = expression.next(ZonedDateTime.now());
            return Date.from(Objects.requireNonNull(next).toInstant());
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
