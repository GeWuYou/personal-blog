package com.gewuyou.blog.server;

import com.gewuyou.blog.common.config.entity.MinioProperties;
import com.gewuyou.blog.common.config.entity.OssConfigProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.gewuyou.blog.server", "com.gewuyou.blog.common"})
@MapperScan(basePackages = {"com.gewuyou.blog.server.mapper", "com.gewuyou.blog.common.mapper"})
@EnableConfigurationProperties({MinioProperties.class, OssConfigProperties.class})
@EnableFeignClients
public class PersonalBlogServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonalBlogServerApplication.class, args);
    }

}
