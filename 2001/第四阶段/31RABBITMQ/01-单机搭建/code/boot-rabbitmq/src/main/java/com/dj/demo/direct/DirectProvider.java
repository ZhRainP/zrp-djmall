package com.dj.demo.direct;

import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 单播-生产者
 */
@RestController
@RequestMapping("/direct")
public class DirectProvider {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMessage1")
    public boolean sendMessage1() {
        // 发送消息
        rabbitTemplate.convertAndSend("direct", "one", "i am one message");
        return true;
    }

    @GetMapping("/sendMessage2")
    public boolean sendMessage2() {
        // 发送消息
        rabbitTemplate.convertAndSend("direct", "two", "i am two message");
        return true;
    }
}
