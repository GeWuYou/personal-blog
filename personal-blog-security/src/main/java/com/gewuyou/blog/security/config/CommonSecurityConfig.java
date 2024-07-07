package com.gewuyou.blog.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 通用安全配置
 *
 * @author gewuyou
 * @since 2024-06-10 下午8:13:07
 */
@Configuration
public class CommonSecurityConfig {
    /**
     * 认证管理器
     *
     * @return org.springframework.security.authentication.AuthenticationManager
     * @apiNote
     * @since 2023/3/22 15:53
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityIgnoreUrl securityIgnoreUrl() {
        return new SecurityIgnoreUrl();
    }

}
