package com.gewuyou.blog.common.constant;

/**
 * Rabbit MQ 常量
 *
 * @author gewuyou
 * @since 2024-05-08 下午3:13:44
 */
public class RabbitMQConstant {
    private RabbitMQConstant() {
    }

    public static final String MAXWELL_QUEUE = "maxwell_queue";

    public static final String MAXWELL_EXCHANGE = "maxwell_exchange";

    public static final String EMAIL_QUEUE = "email_queue";

    public static final String EMAIL_EXCHANGE = "email_exchange";

    public static final String SUBSCRIBE_QUEUE = "subscribe_queue";

    public static final String SUBSCRIBE_EXCHANGE = "subscribe_exchange";
}
