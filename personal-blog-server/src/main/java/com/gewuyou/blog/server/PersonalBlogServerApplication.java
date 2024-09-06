package com.gewuyou.blog.server;

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
@ComponentScan(basePackages = {"com.gewuyou.blog.server", "com.gewuyou.blog.common", "com.gewuyou.blog.security"})
@MapperScan(basePackages = {"com.gewuyou.blog.server.mapper", "com.gewuyou.blog.common.mapper", "com.gewuyou.blog.security.mapper"})
@EnableConfigurationProperties({SecurityIgnoreUrl.class})
@EnableFeignClients
@EnableAspectJAutoProxy
@Slf4j
public class PersonalBlogServerApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(PersonalBlogServerApplication.class, args);
    }

    /**
     * Callback used to run the bean.
     *
     * @param args incoming application arguments
     * @throws Exception on error
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("==>>personal blog server 启动成功<<==");
    }
}
