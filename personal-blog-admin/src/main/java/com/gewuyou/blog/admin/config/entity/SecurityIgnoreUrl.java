package com.gewuyou.blog.admin.config.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 放行请求路径解析
 *
 * @author gewuyou
 * @since 2024-04-16 下午7:29:08
 */
@Configuration
@ConfigurationProperties(prefix = "spring.security.ignore")
@Data
public class SecurityIgnoreUrl {
    /**
     * 需要放行的请求路径数组
     */
    private String[] urls;
}
