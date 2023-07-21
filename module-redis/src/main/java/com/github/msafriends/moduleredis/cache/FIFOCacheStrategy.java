package com.github.msafriends.moduleredis.cache;

/**
 * Interface representing First In First Out (FIFO) caching strategy.
 * Extends EvictionCacheStrategy and allows defining methods specific to FIFO strategy.
 */
public interface FIFOCacheStrategy<KEY, VALUE> extends EvictionCacheStrategy<KEY, VALUE>{
    /**
     * Evicts the oldest item from the cache based on FIFO strategy.
     *
     * @return The evicted item from the cache, or null if no items were evicted.
     */
    VALUE evictOldestItem();
}
