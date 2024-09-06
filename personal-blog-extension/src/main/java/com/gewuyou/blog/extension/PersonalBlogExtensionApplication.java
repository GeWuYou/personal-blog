package com.gewuyou.blog.extension;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class PersonalBlogExtensionApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(PersonalBlogExtensionApplication.class, args);
    }

    /**
     * Callback used to run the bean.
     *
     * @param args incoming application arguments
     */
    @Override
    public void run(ApplicationArguments args) {
        log.info("==>>personal blog extension 启动成功<<==");
    }
}
