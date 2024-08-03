package com.gewuyou.blog.common.config.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 项目配置
 *
 * @author gewuyou
 * @since 2024-08-02 上午10:50:52
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "project")
public class ProjectProperties {
    /**
     * 博主名称
     */
    private String bloggerName;
    /**
     * 网站url
     */
    private String websiteUrl;
}
