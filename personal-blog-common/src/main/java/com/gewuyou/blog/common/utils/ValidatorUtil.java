package com.gewuyou.blog.common.utils;

/**
 * 验证工具
 *
 * @author gewuyou
 * @since 2024-07-30 下午9:30:36
 */
public class ValidatorUtil {
    private static final String EMAIL_PATTERN = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    public static boolean isEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return email.matches(EMAIL_PATTERN);
    }
}
