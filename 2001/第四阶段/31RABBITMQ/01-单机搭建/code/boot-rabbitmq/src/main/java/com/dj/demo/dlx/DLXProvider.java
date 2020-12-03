package com.dj.demo.dlx;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 死信-生产者
 */
@RestController
@RequestMapping("/dlx")
public class DLXProvider {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMessage")
    public boolean sendMessage() {
        // 发送消息
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setDelay(3000);
        Message message = new Message(("i am dlx message time is : "+ + System.currentTimeMillis()).getBytes(), messageProperties);
        rabbitTemplate.convertAndSend("dlx-ex", "dlx", message);
        return true;
    }

}
