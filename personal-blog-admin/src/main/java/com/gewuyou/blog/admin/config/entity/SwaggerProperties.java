package com.gewuyou.blog.admin.config.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Swagger 属性
 *
 * @author gewuyou
 * @since 2024-08-03 下午1:19:42
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {
    private List<String> url;
}
