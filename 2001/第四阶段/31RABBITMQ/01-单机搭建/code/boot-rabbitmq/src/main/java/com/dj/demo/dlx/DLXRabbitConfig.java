package com.dj.demo.dlx;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 死信-配置
 */
@Configuration
public class DLXRabbitConfig {

    /**
     * 死信队列
     *
     * @return
     */
    @Bean
    public Queue dlxQueue() {
        return new Queue("dlx", true);
    }

    /**
     * 死信路由
     *
     * @return
     */
    @Bean
    public CustomExchange dlxExchange() {
        // 死信路由特殊参数
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct"); // 死信路由方式 可选 direct/topic/fanout
        //属性参数 交换机名称 交换机类型 是否持久化 是否自动删除 配置参数
        return new CustomExchange("dlx-ex", "x-delayed-message", true, false, args);
    }

    /**
     * 死信绑定
     * @return
     */
    @Bean
    public Binding dlxBinding() {
        return BindingBuilder.bind(dlxQueue()).to(dlxExchange()).with("dlx").noargs();
    }

}
