package com.gewuyou.blog.common.constant;

/**
 * Redis 常量
 *
 * @author gewuyou
 * @since 2024-04-23 下午10:04:55
 */
public class RedisConstant {
    private RedisConstant() {
    }

    public static final String USER_CODE_KEY = "code:";

    public static final String BLOG_VIEWS_COUNT = "blog_views_count";

    public static final String ARTICLE_VIEWS_COUNT = "article_views_count";

    public static final String WEBSITE_CONFIG = "website_config";

    public static final String USER_AREA = "user_area";

    public static final String VISITOR_AREA = "visitor_area";

    public static final String ABOUT = "about";

    public static final String UNIQUE_VISITOR = "unique_visitor";

    public static final String LOGIN_USER = "login_user";

    public static final String ARTICLE_ACCESS = "article_access:";

    public static final String TOKEN = "token";

    public static final String TEMP_IMAGE_NAME = "temp_image_name";

    public static final String DB_IMAGE_NAME = "db_image_name";

    public static final String IMAGE_LOCK = "image_lock";
}
