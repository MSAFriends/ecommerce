package com.github.msafriends.moduleredis.cache.strategy;

public interface EvictionCacheStrategy<K, V> extends CacheStrategy<K, V>{

    void evictItem(K key);
    void adjustFrequency(K key);
}
