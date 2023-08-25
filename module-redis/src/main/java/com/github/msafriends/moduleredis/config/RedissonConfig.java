package com.github.msafriends.moduleredis.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.stereotype.Component;

@Component
public class RedissonConfig {
    private static final String REDISSON_HOST_PREFIX = "redis://";

    public static RedissonClient createRedissonClient(String host, int port) {
        Config config = new Config();
        config.useSingleServer().setAddress(REDISSON_HOST_PREFIX + host + ":" + port);
        return Redisson.create(config);
    }
}
