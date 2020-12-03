package com.dj.demo.topic;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Topic-生产者
 */
@RestController
@RequestMapping("/topic")
public class TopicProvider {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMessage1")
    public boolean sendMessage1() {
        // 发送消息
        rabbitTemplate.convertAndSend("topic","www.dianit.cn", "i am one message");
        return true;
    }

    @GetMapping("/sendMessage2")
    public boolean sendMessage2() {
        // 发送消息
        rabbitTemplate.convertAndSend("topic","www.baidu.com", "i am two message");
        return true;
    }

    @GetMapping("/sendMessage3")
    public boolean sendMessage3() {
        // 发送消息
        rabbitTemplate.convertAndSend("topic","aa.dianit.bb", "i am three message");
        return true;
    }
}
