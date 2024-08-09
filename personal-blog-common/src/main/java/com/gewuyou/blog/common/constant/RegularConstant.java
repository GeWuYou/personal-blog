package com.gewuyou.blog.common.constant;

/**
 * 正则常量
 *
 * @author gewuyou
 * @since 2024-04-17 下午10:40:50
 */
public class RegularConstant {
    private RegularConstant() {
    }

    /**
     * 用户名正则
     */
    public static final String USERNAME_REGULARITY = "^[a-zA-Z0-9\\u4e00-\\u9fa5]+$";
    /**
     * 邮箱正则
     */
    public static final String EMAIL_REGULARITY = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 文件路径正则
     */
    public static final String FILE_PATH_REGEX = "^https?://[^/]+/api/v1/admin(.*)$";
}
