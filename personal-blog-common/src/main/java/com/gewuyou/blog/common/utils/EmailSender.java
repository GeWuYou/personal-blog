package com.gewuyou.blog.common.utils;

import com.gewuyou.blog.common.exception.GlobalException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * 邮件发送类
 *
 * @author gewuyou
 * @since 2024-04-16 下午8:19:08
 */
@Slf4j
@Component
public class EmailSender {
    /**
     * 邮件发送器
     */
    private final JavaMailSender javaMailSender;

    /**
     * 发送邮箱账号
     */
    @Value("${spring.mail.username}")
    private String emailAccount;
    /**
     * 发送人名称
     */
    @Value("${spring.mail.properties.from}")
    private String emailFrom;

    @Autowired
    public EmailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /**
     * 发送简单邮件
     *
     * @param to      收件人邮箱
     * @param subject 主题
     * @param content 内容
     * @return 发送成功返回true，失败返回false
     * @since 2024/4/16 下午8:26
     */
    public boolean sendSimpleEmail(String to, String subject, String content) {
        try {
            // 创建MimeMessage对象
            MimeMessage message = javaMailSender.createMimeMessage();
            // 创建MemeMessageHelper对象 可选：new MimeMessageHelper(message,true) 携带附件
            MimeMessageHelper messageHelper = new MimeMessageHelper(message);
            // 设定发送人
            messageHelper.setFrom(emailAccount, emailFrom);
            // 设定发送对象
            messageHelper.setTo(to);
            // 设定邮件主题
            messageHelper.setSubject(subject);
            // 设定邮件内容
            messageHelper.setText(content);
            // 发送邮件
            javaMailSender.send(message);
            return true;
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("邮件发送失败", e);
            return false;
        }
    }

}
