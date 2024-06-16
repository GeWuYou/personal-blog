package com.gewuyou.blog.common.utils;

import java.util.*;

/**
 * 通用工具
 *
 * @author gewuyou
 * @since 2024-06-16 下午3:48:20
 */
public class CommonUtil {
    public static String getBracketsContent(String str) {
        return str.substring(str.indexOf("(") + 1, str.indexOf(")"));
    }

    public static String getRandomCode() {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }

    public static <T> List<T> castList(Object obj, Class<T> clazz) {
        List<T> result = new ArrayList<T>();
        if (obj instanceof List<?>) {
            for (Object o : (List<?>) obj) {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return result;
    }

    public static <T> Set<T> castSet(Object obj, Class<T> clazz) {
        Set<T> result = new HashSet<>();
        if (obj instanceof Set<?>) {
            for (Object o : (Set<?>) obj) {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return result;
    }
}
