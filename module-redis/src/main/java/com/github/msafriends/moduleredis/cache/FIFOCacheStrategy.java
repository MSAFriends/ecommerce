package com.github.msafriends.moduleredis.cache;

/**
 * Interface representing First In First Out (FIFO) caching strategy.
 * Extends EvictionCacheStrategy and allows defining methods specific to FIFO strategy.
 */
public interface FIFOCacheStrategy<K, V> extends EvictionCacheStrategy<K, V>{
    /**
     * Evicts the oldest item from the cache based on FIFO strategy.
     *
     * @return The evicted item from the cache, or null if no items were evicted.
     */
    V evictOldestItem();
}
