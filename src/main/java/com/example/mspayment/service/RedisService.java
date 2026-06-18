package com.example.mspayment.service;

import com.example.mspayment.dao.entity.Payment;
import com.example.mspayment.dto.PaymentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String, PaymentResponseDto> redisTemplate;

    public void setValue(String key, PaymentResponseDto value, long ttlSeconds) {
        redisTemplate.opsForValue().set(key, value,  ttlSeconds, TimeUnit.SECONDS);
    }

    public PaymentResponseDto getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteKey(String key) {
        redisTemplate.delete(key);
    }
}
