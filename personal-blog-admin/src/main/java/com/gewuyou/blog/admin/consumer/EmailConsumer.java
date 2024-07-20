package com.gewuyou.blog.admin.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gewuyou.blog.common.dto.EmailDTO;
import com.gewuyou.blog.common.utils.EmailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static com.gewuyou.blog.common.constant.RabbitMQConstant.EMAIL_QUEUE;

/**
 * 邮件消费者
 *
 * @author gewuyou
 * @since 2024-07-20 下午3:06:57
 */
@Component
@RabbitListener(queues = EMAIL_QUEUE)
@Slf4j
public class EmailConsumer {
    private final EmailUtil emailUtil;
    private final ObjectMapper objectMapper;

    @Autowired
    public EmailConsumer(EmailUtil emailUtil, @Qualifier("objectMapper") ObjectMapper objectMapper) {
        this.emailUtil = emailUtil;
        this.objectMapper = objectMapper;
    }

    @RabbitHandler
    public void process(byte[] data) {
        // 打印消息体
        log.info("Sending emailDTO: {}", new String(data));
        try {
            emailUtil.sendHtmlEmail(objectMapper.readValue(data, EmailDTO.class));
        } catch (Exception e) {
            log.error("邮件发送失败", e);
        }
    }
}
