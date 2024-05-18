package com.gewuyou.blog.common.constant;

/**
 * 接口常量
 *
 * @author gewuyou
 * @since 2024-04-19 下午7:22:56
 */
public class InterfacePermissionConstant {
    private InterfacePermissionConstant() {
    }

    /**
     * 路径前缀
     */
    public static final String API_PREFIX = "/api";

    /**
     * 版本号
     */
    public static final String VERSION = "v1";

    public static final String ADMIN = "admin";

    public static final String SERVER = "server";

    public static final String SEARCH = "search";

    public static final String ADMIN_BASE_URL = API_PREFIX + "/" + VERSION + "/" + ADMIN;

    public static final String SERVER_BASE_URL = API_PREFIX + "/" + VERSION + "/" + SERVER;

    public static final String SEARCH_BASE_URL = API_PREFIX + "/" + VERSION + "/" + SEARCH;
}
