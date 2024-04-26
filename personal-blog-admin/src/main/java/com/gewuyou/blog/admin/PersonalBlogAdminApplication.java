package com.gewuyou.blog.admin;

import com.gewuyou.blog.admin.config.entity.SecurityIgnoreUrl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
// 大坑，当配置了扫描路径后需要显示的指定所有需要扫描的路径
@ComponentScan(basePackages = {"com.gewuyou.blog.common", "com.gewuyou.blog.admin"})
@EnableConfigurationProperties(SecurityIgnoreUrl.class)
@EnableFeignClients
public class PersonalBlogAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonalBlogAdminApplication.class, args);
    }

}
