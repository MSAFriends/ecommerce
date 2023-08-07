package com.github.msafriends.serviceproduct.moduleapi.service;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisLockRepository {
    private final RedisTemplate<String, String> redisTemplate;

    public Boolean lock(Long key){
        return redisTemplate.opsForValue()
            .setIfAbsent(key.toString(), "lock", Duration.ofMillis(3_000));
    }

    public Boolean unlock(Long key){
        return redisTemplate.delete(key.toString());
    }
}
