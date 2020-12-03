package com.dj.demo.topic;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Topic-消费者
 */
@Component
public class TopicConsumer {

    /**
     * 消费者1
     * @param message 消息体
     * @throws Exception
     */
    @RabbitHandler
    @RabbitListener(queues = "topic.queue1")
    public void process1(Message message) throws Exception {
        System.out.println("TopicConsumer1: " + new String(message.getBody(), "UTF-8"));
    }

    /**
     * 消费者2
     * @param message 消息体
     * @throws Exception
     */
    @RabbitHandler
    @RabbitListener(queues = "topic.queue2")
    public void process2(Message message) throws Exception {
        System.out.println("TopicConsumer2: " + new String(message.getBody(), "UTF-8"));
    }
}
