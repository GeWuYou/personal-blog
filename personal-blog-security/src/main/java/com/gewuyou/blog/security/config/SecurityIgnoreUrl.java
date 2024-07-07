package com.gewuyou.blog.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 放行请求路径解析
 *
 * @author gewuyou
 * @since 2024-04-16 下午7:29:08
 */
@ConfigurationProperties(prefix = "spring.security.ignore")
@Data
public class SecurityIgnoreUrl {
    /**
     * 需要放行的请求路径数组
     */
    private String[] urls;
}
