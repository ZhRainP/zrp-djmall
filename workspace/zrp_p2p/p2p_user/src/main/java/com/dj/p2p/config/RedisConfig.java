package com.dj.p2p.config;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis 配置器
 */
@Configuration
public class RedisConfig {

    /**
     * 设置RedisTemplate
     *
     * @param redisConnectionFactory 连接池工厂
     * @return
     */
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {

        RedisTemplate redisTemplate = new RedisTemplate();
        // 开启自动类型支持
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        // FastJSON序列化
        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
        // 序列化时写入类的类型
        fastJsonRedisSerializer.getFastJsonConfig().setSerializerFeatures(SerializerFeature.WriteClassName);

        // key的序列化采用StringRedisSerializer
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        // 值序列化JSON
        redisTemplate.setValueSerializer(fastJsonRedisSerializer);
        redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);

        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }
}
