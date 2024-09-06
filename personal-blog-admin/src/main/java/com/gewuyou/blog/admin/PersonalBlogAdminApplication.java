package com.gewuyou.blog.admin;

import com.gewuyou.blog.admin.config.entity.LocalProperties;
import com.gewuyou.blog.admin.config.entity.MinioProperties;
import com.gewuyou.blog.admin.config.entity.OssConfigProperties;
import com.gewuyou.blog.admin.config.entity.QiNiuProperties;
import com.gewuyou.blog.security.config.SecurityIgnoreUrl;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
// 大坑，当配置了扫描路径后需要显式的指定所有需要扫描的路径
@ComponentScan(basePackages = {"com.gewuyou.blog.common", "com.gewuyou.blog.admin", "com.gewuyou.blog.security"})
@MapperScan(basePackages = {"com.gewuyou.blog.admin.mapper", "com.gewuyou.blog.common.mapper", "com.gewuyou.blog.security.mapper"})
@EnableConfigurationProperties({SecurityIgnoreUrl.class, MinioProperties.class, OssConfigProperties.class, QiNiuProperties.class, LocalProperties.class})
@EnableFeignClients
@EnableAspectJAutoProxy
@Slf4j
public class PersonalBlogAdminApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(PersonalBlogAdminApplication.class, args);
    }

    /**
     * Callback used to run the bean.
     *
     * @param args incoming application arguments
     */
    @Override
    public void run(ApplicationArguments args) {
        log.info("==>>personal blog admin 启动成功<<==");
    }
}
