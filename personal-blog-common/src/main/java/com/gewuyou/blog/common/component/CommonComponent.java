package com.gewuyou.blog.common.component;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

/**
 * 公共组件
 *
 * @author gewuyou
 * @since 2024-04-19 下午5:49:25
 */
@Component
public class CommonComponent {
    @Bean
    public JavaMailSender getJavaMailSender() {
        return new JavaMailSenderImpl();
    }
}
