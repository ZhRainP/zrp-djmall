package com.dj.demo.fanout;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 广播-生产者
 */
@RestController
@RequestMapping("/fanout")
public class FanoutProvider {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMessage1")
    public boolean sendMessage1() {
        // 发送消息
        rabbitTemplate.convertAndSend("fanout","fanoutQueue1", "i am one message");
        return true;
    }

    @GetMapping("/sendMessage2")
    public boolean sendMessage2() {
        // 发送消息
        rabbitTemplate.convertAndSend("fanout","fanoutQueue2", "i am two message");
        return true;
    }
}
