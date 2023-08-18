package com.github.msafriends.moduleredis.config;

import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CustomCachingManager extends AbstractCacheManager {
    private final RedisCacheManager redisCacheManager;
    private final Map<String, Cache> customCaches = new ConcurrentHashMap<>();

    public CustomCachingManager(RedisConnectionFactory redisConnectionFactory) {
        this.redisCacheManager = RedisCacheManager.builder(redisConnectionFactory).build();
    }

    @Override
    protected List<? extends Cache> loadCaches() {
        return Arrays.asList(
                redisCacheManager.getCache("default"), // 기본 Redis 캐시 설정
                getMissingCache("FIFOCacheWithWriteBack"),
                getMissingCache("LFUCacheWithWriteThrough")
        );
    }

    @Override
    protected Cache getMissingCache(String name) {
//        if ("FIFOCacheWithWriteBack".equals(name)) {
//            return (Cache) new FIFOCacheWithWriteBack<>();
//        } else if ("LFUCacheWithWriteThrough".equals(name)) {
//            return (Cache) new LFUCacheWithWriteThrough<>();
//        }
        return null;
    }

    @Override
    public Cache getCache(String name) {
        return customCaches.computeIfAbsent(name, key -> super.getCache(key));
    }
}
