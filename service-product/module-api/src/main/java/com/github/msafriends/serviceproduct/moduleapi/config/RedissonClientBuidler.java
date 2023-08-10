package com.github.msafriends.serviceproduct.moduleapi.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;

public class RedissonClientBuidler {
    public static RedissonClient buildRedissonClient(){
        Config config = new Config().setCodec(StringCodec.INSTANCE);
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress("redis://localhost:6379");
        return Redisson.create(config);
    }
}
