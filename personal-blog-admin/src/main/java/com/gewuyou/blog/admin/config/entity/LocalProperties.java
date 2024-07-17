package com.gewuyou.blog.admin.config.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 本地配置属性
 *
 * @author gewuyou
 * @since 2024-07-16 下午11:20:05
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "upload.local")
public class LocalProperties {
    /**
     * 图片本地存储根路径
     */
    private String root;

    /**
     * 图片url
     */
    private String url;
}
