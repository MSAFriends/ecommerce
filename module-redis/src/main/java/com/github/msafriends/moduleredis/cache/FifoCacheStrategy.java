package com.github.msafriends.moduleredis.cache;

public interface FifoCacheStrategy<KEY, VALUE> extends EvictionCacheStrategy<KEY, VALUE>{
    void evictOldestItem();
}
