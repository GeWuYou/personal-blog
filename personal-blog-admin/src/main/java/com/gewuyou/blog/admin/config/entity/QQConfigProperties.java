package com.gewuyou.blog.admin.config.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * QQ 配置属性
 *
 * @author gewuyou
 * @since 2024-07-18 下午12:14:29
 */
@Schema(description = "QQ 配置属性")
@Data
@Configuration
@ConfigurationProperties(prefix = "qq")
public class QQConfigProperties {

    /**
     * QQ 应用 ID
     */
    @Schema(description = "QQ 应用 ID")
    private String appId;

    /**
     * QQ 检查 Token URL
     */
    @Schema(description = "QQ 检查 Token URL")
    private String checkTokenUrl;

    /**
     * QQ 获取用户信息 URL
     */
    @Schema(description = "QQ 获取用户信息 URL")
    private String userInfoUrl;
}
