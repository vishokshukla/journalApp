package com.learnboot.journalapp.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Disabled
    @Test
    void testRedis() {
        redisTemplate.opsForValue().set("email", "vishok@email.com");
        redisTemplate.opsForValue().set("email1", "vishokk@email.com");
        Object email = redisTemplate.opsForValue().get("email");
        Object salary = redisTemplate.opsForValue().get("salary");
        System.out.println(email + " " + salary);
    }
}
