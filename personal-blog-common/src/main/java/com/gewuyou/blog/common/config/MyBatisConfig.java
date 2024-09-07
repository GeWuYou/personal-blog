package com.gewuyou.blog.common.config;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.gewuyou.blog.common.interceptor.SqlInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis 配置
 *
 * @author gewuyou
 * @since 2024-09-07 17:13:54
 */
@Configuration
public class MyBatisConfig {
    @Bean
    public ConfigurationCustomizer mybatisConfigurationCustomizer() {
        return configuration -> configuration.addInterceptor(new SqlInterceptor());
    }
}
