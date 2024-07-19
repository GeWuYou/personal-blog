package com.gewuyou.blog.common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 日期工具
 *
 * @author gewuyou
 * @since 2024-07-10 下午5:57:45
 */
public class DateUtil {
    /**
     * 获取指定日期的开始时间（即00:00:00）
     *
     * @param date 指定日期
     * @return 指定日期的开始时间
     */
    public static Date beginOfDay(Date date) {
        LocalDate localDate = convertToLocalDate(date);
        LocalDateTime startOfDay = localDate.atStartOfDay();
        return convertToDate(startOfDay);
    }

    /**
     * 获取指定日期的结束时间（即23:59:59.999）
     *
     * @param date 指定日期
     * @return 指定日期的结束时间
     */
    public static Date endOfDay(Date date) {
        LocalDate localDate = convertToLocalDate(date);
        LocalDateTime endOfDay = localDate.atTime(LocalTime.MAX);
        return convertToDate(endOfDay);
    }

    /**
     * 根据天数偏移日期
     *
     * @param date 基础日期
     * @param days 偏移天数（负数表示向前，正数表示向后）
     * @return 偏移后的日期
     */
    public static Date offsetDay(Date date, int days) {
        LocalDate localDate = convertToLocalDate(date);
        LocalDate offsetDate = localDate.plusDays(days);
        return convertToDate(offsetDate.atStartOfDay());
    }

    /**
     * 将 Date 转换为 LocalDate
     *
     * @param date 需要转换的 Date
     * @return 转换后的 LocalDate
     */
    public static LocalDate convertToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * 将 Date 转换为 LocalDateTime
     *
     * @param date 需要转换的 Date
     * @return 转换后的 LocalDateTime
     */
    public static LocalDateTime convertToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
    /**
     * 将 LocalDateTime 转换为 Date
     *
     * @param dateTime 需要转换的 LocalDateTime
     * @return 转换后的 Date
     */
    public static Date convertToDate(LocalDateTime dateTime) {
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

}
