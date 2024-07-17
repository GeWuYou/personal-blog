package com.gewuyou.blog.admin.config.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Oss 配置属性
 *
 * @author gewuyou
 * @since 2024-05-06 下午7:35:49
 */
@Configuration
@ConfigurationProperties(prefix = "upload.oss")
@Data
public class OssConfigProperties {
    /**
     * oss url
     */
    private String url;

    /**
     * oss 服务器终端节点
     */
    private String endpoint;
    /**
     * oss AccessKeyId
     */
    private String accessKeyId;

    /**
     * oss SecretAccessKey
     */
    private String secretAccessKey;

    /**
     * oss bucket名称
     */
    private String bucketName;
}
