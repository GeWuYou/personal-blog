package com.gewuyou.blog.common.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.gewuyou.blog.common.constant.RabbitMQConstant.*;

/**
 * Rabbit MQ 配置
 *
 * @author gewuyou
 * @since 2024-05-08 下午2:59:04
 */
@Configuration
public class RabbitMQConfig {
    /**
     * 定义文章队列
     *
     * @return 文章队列
     */
    @Bean
    public Queue articleQueue() {
        return new Queue(MAXWELL_QUEUE, true);
    }

    /**
     * 定义 MaxWell 交换机
     *
     * @return MaxWell 交换机
     */
    @Bean
    public FanoutExchange maxWellExchange() {
        return new FanoutExchange(MAXWELL_EXCHANGE, true, false);
    }

    /**
     * 将文章队列绑定到 MaxWell 交换机
     *
     * @return 绑定对象
     */
    @Bean
    public Binding bindingArticleDirect() {
        return BindingBuilder.bind(articleQueue()).to(maxWellExchange());
    }

    /**
     * 定义邮件队列
     *
     * @return 邮件队列
     */
    @Bean
    public Queue emailQueue() {
        return new Queue(EMAIL_QUEUE, true);
    }

    /**
     * 定义邮件交换机
     *
     * @return 邮件交换机
     */
    @Bean
    public FanoutExchange emailExchange() {
        return new FanoutExchange(EMAIL_EXCHANGE, true, false);
    }

    /**
     * 将邮件队列绑定到邮件交换机
     *
     * @return 绑定对象
     */
    @Bean
    public Binding bindingEmailDirect() {
        return BindingBuilder.bind(emailQueue()).to(emailExchange());
    }

    /**
     * 定义订阅队列
     *
     * @return 订阅队列
     */
    @Bean
    public Queue subscribeQueue() {
        return new Queue(SUBSCRIBE_QUEUE, true);
    }

    /**
     * 定义订阅交换机
     *
     * @return 订阅交换机
     */
    @Bean
    public FanoutExchange subscribeExchange() {
        return new FanoutExchange(SUBSCRIBE_EXCHANGE, true, false);
    }

    /**
     * 将订阅队列绑定到订阅交换机
     *
     * @return 绑定对象
     */
    @Bean
    public Binding bindingSubscribeDirect() {
        return BindingBuilder.bind(subscribeQueue()).to(subscribeExchange());
    }
}
