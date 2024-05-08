package com.gewuyou.blog.common.config.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Minio 配置
 *
 * @author gewuyou
 * @since 2024-05-06 下午7:23:55
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "upload.minio")
public class MinioProperties {
    /**
     * Minio 地址
     */
    private String url;

    /**
     * Minio 服务器终端节点
     */
    private String endpoint;

    /**
     * Minio 用户名
     */
    private String accessKey;

    /**
     * Minio 密码
     */
    private String secretKey;

    /**
     * Minio 存储桶名称
     */
    private String bucketName;
}
