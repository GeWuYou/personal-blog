package com.gewuyou.blog.eureka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
@Slf4j
public class PersonalBlogEurekaApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(PersonalBlogEurekaApplication.class, args);
	}

    /**
     * Callback used to run the bean.
     *
     * @param args incoming application arguments
     */
    @Override
    public void run(ApplicationArguments args) {
        log.info("==>>personal blog eureka 启动成功<<==");
    }
}
