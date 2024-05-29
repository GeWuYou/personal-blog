package com.gewuyou.blog.admin;

import com.gewuyou.blog.admin.config.entity.SecurityIgnoreUrl;
import com.gewuyou.blog.common.config.entity.MinioProperties;
import com.gewuyou.blog.common.config.entity.OssConfigProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
// 大坑，当配置了扫描路径后需要显示的指定所有需要扫描的路径
@ComponentScan(basePackages = {"com.gewuyou.blog.common", "com.gewuyou.blog.admin"})
@MapperScan(basePackages = {"com.gewuyou.blog.admin.mapper", "com.gewuyou.blog.common.mapper"})
@EnableConfigurationProperties({SecurityIgnoreUrl.class, MinioProperties.class, OssConfigProperties.class})
@EnableFeignClients
public class PersonalBlogAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonalBlogAdminApplication.class, args);
    }

}
