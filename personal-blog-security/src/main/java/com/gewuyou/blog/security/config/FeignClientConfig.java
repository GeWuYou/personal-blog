package com.gewuyou.blog.security.config;

import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Feign 客户端配置
 *
 * @author gewuyou
 * @since 2024-07-16 下午4:22:36
 */
@Configuration
@Slf4j
public class FeignClientConfig {
    @Value("${jwt.internal.secretKey}")
    private String internalSecretKey;

    @Bean
    public RequestInterceptor jwtTokenInterceptor() {
        return template -> template.header("Internal-Request-SecretKey", internalSecretKey);
    }
}
