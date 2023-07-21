package com.github.msafriends.moduleredis.cache;

public interface EvictionCacheStrategy<K, V> extends CacheStrategy<K, V>{

    void evictItem(K key);
    void adjustFrequency(K key);
}
