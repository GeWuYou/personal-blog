package com.gewuyou.blog.common.config;

import com.gewuyou.blog.common.config.entity.MailProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * 邮箱配置
 *
 * @author gewuyou
 * @since 2024-07-20 下午6:37:45
 */
@Configuration
public class EmailConfig {

    private final MailProperties mailProperties;

    @Autowired
    public EmailConfig(MailProperties mailProperties) {
        this.mailProperties = mailProperties;
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
        javaMailSenderImpl.setHost(mailProperties.getHost());
        javaMailSenderImpl.setUsername(mailProperties.getUsername());
        javaMailSenderImpl.setPassword(mailProperties.getPassword());
        javaMailSenderImpl.setDefaultEncoding(mailProperties.getDefaultEncoding());
        javaMailSenderImpl.setPort(mailProperties.getPort());
        javaMailSenderImpl.setProtocol(mailProperties.getProtocol());
        javaMailSenderImpl.setJavaMailProperties(mailProperties.getProperties());
        return javaMailSenderImpl;
    }
}
