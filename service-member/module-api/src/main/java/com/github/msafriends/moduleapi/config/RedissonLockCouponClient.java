package com.github.msafriends.moduleapi.config;

import com.github.msafriends.moduleapi.service.MemberCouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedissonLockCouponClient {
    private final RedissonClient redissonClient;
    private final MemberCouponService memberCouponService;
}
