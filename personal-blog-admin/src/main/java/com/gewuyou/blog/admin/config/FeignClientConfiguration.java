package com.gewuyou.blog.admin.config;

import com.gewuyou.blog.common.utils.UserUtil;
import com.gewuyou.blog.security.service.JwtService;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * Feign 客户端配置
 *
 * @author gewuyou
 * @since 2024-07-16 下午4:22:36
 */
@Configuration
public class FeignClientConfiguration {
    @Bean
    @Lazy
    public RequestInterceptor jwtTokenInterceptor(JwtService jwtService) {
        return template -> {
            String token = jwtService.getToken(UserUtil.getUserDetailsDTO().getUserAuthId());
            if (token != null && !token.isEmpty()) {
                template.header("Authorization", "Bearer " + token);
            }
        };
    }
}
