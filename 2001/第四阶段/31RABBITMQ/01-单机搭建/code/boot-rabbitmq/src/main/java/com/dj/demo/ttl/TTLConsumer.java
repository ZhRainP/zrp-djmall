package com.dj.demo.ttl;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * TTL-消费者
 */
@Component
public class TTLConsumer {

    /**
     * 消费者
     *
     * @param message 消息体
     * @throws Exception
     */
    @RabbitHandler
    @RabbitListener(queues = "ttl-queue")
    public void process(Message message) throws Exception {
        System.out.println("TTLConsumer process time is : " + System.currentTimeMillis() + " - " + new String(message.getBody(), "UTF-8"));
    }

}
