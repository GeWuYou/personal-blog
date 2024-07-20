package com.gewuyou.blog.common.utils;

import com.gewuyou.blog.common.dto.EmailDTO;
import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.exception.GlobalException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;

/**
 * 邮件发送类
 *
 * @author gewuyou
 * @since 2024-04-16 下午8:19:08
 */
@Slf4j
@Component
public class EmailUtil {
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

    private final TemplateEngine templateEngine;

    @Autowired
    public EmailUtil(
            JavaMailSender javaMailSender,
            TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
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
    public void sendSimpleEmail(String to, String subject, String content) {
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
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("邮件发送失败", e);
            throw new GlobalException(ResponseInformation.SEND_EMAIL_FAILED);
        }
    }

    public void sendHtmlEmail(EmailDTO emailDTO) {
        try {
            // 创建MimeMessage对象
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            // 创建MemeMessageHelper对象 可选：new MimeMessageHelper(mimeMessage,true) 携带附件
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            Context context = new Context();
            context.setVariables(emailDTO.getContentMap());
            // 邮件模板
            String process = templateEngine.process(emailDTO.getTemplate(), context);
            // 设定发送人
            messageHelper.setFrom(emailAccount, emailFrom);
            // 设定发送对象
            messageHelper.setTo(emailDTO.getEmail());
            // 设定邮件主题
            messageHelper.setSubject(emailDTO.getSubject());
            // 设定邮件内容
            messageHelper.setText(process, true);
            // 发送邮件
            javaMailSender.send(mimeMessage);
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("邮件发送失败", e);
            throw new GlobalException(ResponseInformation.SEND_EMAIL_FAILED);
        }
    }
}
