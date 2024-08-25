package com.gewuyou.blog.server;

import com.gewuyou.blog.security.config.SecurityIgnoreUrl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@ComponentScan(basePackages = {"com.gewuyou.blog.server", "com.gewuyou.blog.common", "com.gewuyou.blog.security"})
@MapperScan(basePackages = {"com.gewuyou.blog.server.mapper", "com.gewuyou.blog.common.mapper", "com.gewuyou.blog.security.mapper"})
@EnableConfigurationProperties({SecurityIgnoreUrl.class})
@EnableFeignClients
@EnableAspectJAutoProxy
public class PersonalBlogServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonalBlogServerApplication.class, args);
    }

}
