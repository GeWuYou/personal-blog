package com.gewuyou.blog.common.utils;

import java.util.Random;

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

    /**
     * 生成随机码
     *
     * @param length 随机码长度
     * @return 随机码
     */
    public static String getRandomCode(int length) {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }
}
