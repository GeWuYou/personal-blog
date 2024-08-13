package com.gewuyou.blog.common.config;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Redisson 配置
 *
 * @author gewuyou
 * @since 2024-08-08 18:58:14
 */
@Configuration
@Slf4j
public class RedissonConfig {
    @Value("${redisson.host}")
    private String host;
    @Value("${redisson.database}")
    private Integer database;
    @Value("${redisson.password}")
    private String password;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        SingleServerConfig singleServerConfig = config
                .useSingleServer()
                .setAddress(host)
                .setDatabase(database);
        if (password != null && !password.isEmpty()) {
            singleServerConfig.setPassword(password);
        }
        return Redisson.create(config);
    }
}
