package com.gewuyou.blog.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class PersonalBlogGatewayApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(PersonalBlogGatewayApplication.class, args);
    }

    /**
     * Callback used to run the bean.
     *
     * @param args incoming application arguments
     * @throws Exception on error
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("==>>personal blog gateway 启动成功<<==");
    }
}
