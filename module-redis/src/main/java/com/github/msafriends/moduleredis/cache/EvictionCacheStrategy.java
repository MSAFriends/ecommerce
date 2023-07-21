package com.github.msafriends.moduleredis.cache;

public interface EvictionCacheStrategy<KEY, VALUE> extends CacheStrategy<KEY, VALUE>{

    void evictItem(KEY key);
    void adjustFrequency(KEY key);
}
