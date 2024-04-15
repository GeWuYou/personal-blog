package com.gewuyou.blog.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class PersonalBlogEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonalBlogEurekaApplication.class, args);
	}

}
