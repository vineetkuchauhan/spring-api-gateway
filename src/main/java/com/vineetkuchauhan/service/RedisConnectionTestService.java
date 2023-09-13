package com.vineetkuchauhan.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RedisConnectionTestService {

    private final StringRedisTemplate redisTemplate;

    @Autowired
    public RedisConnectionTestService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void testRedisConnection() {
        try {
            redisTemplate.opsForValue().set("test_key", "Hello1, Second time Redis!");
            String value = redisTemplate.opsForValue().get("test_key");
            System.out.println("Value from Redis: " + value);
        } catch (Exception e) {
            System.err.println("Error connecting to Redis: " + e.getMessage());
        }
    }
}

