package com.gewuyou.blog.common.config;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * Redisson 配置
 *
 * @author gewuyou
 * @since 2024-08-08 18:58:14
 */
@Configuration
@Slf4j
public class RedissonConfig {
    @Bean
    public RedissonClient redissonClient() {
        try {
            Config config = Config.fromYAML(getClass().getClassLoader().getResource("redisson.yml"));
            return Redisson.create(config);
        } catch (IOException e) {
            log.error("Redisson 配置文件加载失败", e);
            throw new RuntimeException(e);
        }
    }
}
