package com.werp.sero.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Component
public class RedisUtil {
    private final StringRedisTemplate redisTemplate;

    public void setData(final String key, final String value, final long duration, final TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, duration, timeUnit);
    }

    public String getData(final String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteData(final String key) {
        redisTemplate.delete(key);
    }
}