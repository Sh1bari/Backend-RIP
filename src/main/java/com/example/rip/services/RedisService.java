package com.example.rip.services;

import lombok.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;


    public void saveToRedis(String key, String value) {

        redisTemplate.opsForValue().set(key, value);
    }

    public String getFromRedis(String key) {

        return (String) redisTemplate.opsForValue().get(key);
    }
}
