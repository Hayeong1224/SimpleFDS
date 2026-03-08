package com.example.core.fds.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class TransferCountTracker {

    private final StringRedisTemplate redisTemplate;

    // TTL 5분 동안 누적 횟수 추적
    // 같은 fromAccountId로 거래 -> 1 증가
    public long increaseAndGetCount(Long fromAccountId) {
        // toString() - Null이면 NullPointerException
        // String.ValueOf() - Null이면 "null"
        String key = "fds:transfer:count" + Long.toString(fromAccountId);

        Long count = redisTemplate.opsForValue().increment(key);
        redisTemplate.expire(key, Duration.ofMinutes(5));

        return count == null ? 0L : count;
    }
}
