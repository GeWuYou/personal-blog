package com.gewuyou.blog.common.config.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 七牛云配置
 *
 * @author gewuyou
 * @since 2024-07-14 下午9:10:55
 */
@Configuration
@ConfigurationProperties(prefix = "upload.qiniu")
@Data
public class QiNiuProperties {
    /**
     * 七牛云的域名
     */
    private String url;
    /**
     * 七牛云的accessKey
     */
    private String accessKey;
    /**
     * 七牛云的secretKey
     */
    private String secretKey;
    /**
     * 七牛云的bucketName
     */
    private String bucketName;
    /**
     * 七牛云的终端节点
     */
    private String endpoint;
}
