package com.gewuyou.blog.encrypt;

import com.gewuyou.blog.encrypt.config.KeyConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author gewuyou
 * @since 2024-04-14 下午8:14:57
 */
@SpringBootApplication
@EnableConfigurationProperties(KeyConfig.class)
public class PersonalBlogEncryptApplication {
    public static void main(String[] args) {
        SpringApplication.run(PersonalBlogEncryptApplication.class, args);
    }
}
