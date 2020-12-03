package com.dj.demo.fanout;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 广播-配置
 */
@Configuration
public class FanoutRabbitConfig {

    /**
     * 广播队列1
     *
     * @return
     */
    @Bean
    public Queue fanoutQueue1() {
        // name=队列的名称
        // durable 是否持久化
        // exclusive 是否独占队列
        // autoDelete 是否自动删除
        return new Queue("fanoutQueue1", true);
    }

    /**
     * 广播队列2
     *
     * @return
     */
    @Bean
    public Queue fanoutQueue2() {
        return new Queue("fanoutQueue2", true);
    }

    /**
     * 创建广播路由器
     *
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanout");
    }

    /**
     * 队列绑定1
     *
     * @return
     */
    @Bean
    public Binding bindingFanoutQueue1() {
        return BindingBuilder.bind(fanoutQueue1()).to(fanoutExchange());
    }

    /**
     * 队列绑定2
     *
     * @return
     */
    @Bean
    public Binding bindingFanoutQueue2() {
        return BindingBuilder.bind(fanoutQueue2()).to(fanoutExchange());
    }
}
