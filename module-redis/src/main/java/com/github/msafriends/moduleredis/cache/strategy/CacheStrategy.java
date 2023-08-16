package com.github.msafriends.moduleredis.cache.strategy;

import org.springframework.stereotype.Indexed;

import javax.cache.Cache;

@Indexed
public interface CacheStrategy <K, V> extends Cache<K, V>{
    void clear();
    int getSize();
    boolean contains(K key);
}
