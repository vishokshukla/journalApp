package com.learnboot.journalapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnboot.journalapp.api.response.MiniWeatherResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public <T> T getValue(String key, Class<T> responseTypeClass) {
        try {
            Object object = redisTemplate.opsForValue().get(key);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(object.toString(), responseTypeClass);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public void setValue(String key, Object o, Long ttl) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String s = objectMapper.writeValueAsString(o);
            redisTemplate.opsForValue().set(key, s, ttl, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
