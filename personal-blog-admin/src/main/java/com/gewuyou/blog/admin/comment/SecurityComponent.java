package com.gewuyou.blog.admin.comment;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Security组件
 *
 * @author gewuyou
 * @since 2024-04-18 下午10:22:13
 */
@Component
public class SecurityComponent {
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

    /**
     * 密码编码器
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
