package com.example.mspayment.config;

import com.example.mspayment.dao.entity.Payment;
import com.example.mspayment.dto.PaymentResponseDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, PaymentResponseDto> redisTemplate(LettuceConnectionFactory connectionFactory) {

        RedisTemplate<String, PaymentResponseDto> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(connectionFactory);

        redisTemplate.setKeySerializer(new StringRedisSerializer());

        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }
}
