package com.gewuyou.blog.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.gewuyou.blog.server", "com.gewuyou.blog.common"})
public class PersonalBlogServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonalBlogServerApplication.class, args);
    }

}
