package com.dj.demo.dlx;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 死信-消费者
 */
@Component
public class DLXConsumer {

    /**
     * 消费者
     *
     * @param message 消息体
     * @throws Exception
     */
    @RabbitHandler
    @RabbitListener(queues = "dlx")
    public void process1(Message message) throws Exception {
        System.out.println("DLXConsumer: " + new String(message.getBody(), "UTF-8"));
    }

}
