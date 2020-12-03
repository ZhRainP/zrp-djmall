package com.dj.demo.direct;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 单播-消费者
 */
@Component
public class DirectConsumer {

    /**
     * 消费者1
     * @param message 消息体
     * @throws Exception
     */
    @RabbitHandler
    @RabbitListener(queues = "directQueue1")
    public void process1(Message message) throws Exception {
        System.out.println("DirectConsumer1: " + new String(message.getBody(), "UTF-8"));
    }

    /**
     * 消费者2
     * @param message 消息体
     * @throws Exception
     */
    @RabbitHandler
    @RabbitListener(queues = "directQueue2")
    public void process2(Message message) throws Exception {
        System.out.println("DirectConsumer2: " + new String(message.getBody(), "UTF-8"));
    }
}
