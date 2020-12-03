package com.dj.demo.topic;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Topic-配置
 */
@Configuration
public class TopicRabbitConfig {

    /**
     * 广播队列1
     *
     * @return
     */
    @Bean
    public Queue queue1() {
        // name=队列的名称
        // durable 是否持久化
        // exclusive 是否独占队列
        // autoDelete 是否自动删除
        return new Queue("topic.queue1", true);
    }

    /**
     * 广播队列2
     *
     * @return
     */
    @Bean
    public Queue queue2() {
        return new Queue("topic.queue2", true);
    }

    /**
     * 创建Topic路由器
     *
     * @return
     */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("topic");
    }

    /**
     * 队列绑定1
     *
     * @return
     */
    @Bean
    public Binding bindingTopicQueue1() {
        // Message的routingKey与其一致
        return BindingBuilder.bind(queue1()).to(topicExchange()).with("*.dianit.*");
    }

    /**
     * 队列绑定2
     *
     * @return
     */
    @Bean
    public Binding bindingTopicQueue2() {
        // Message的routingKey与其一致
        return BindingBuilder.bind(queue2()).to(topicExchange()).with("*.baidu.*");
    }
}
