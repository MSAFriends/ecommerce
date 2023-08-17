package com.github.msafriends.moduleredis.cache.strategy;

import java.time.Duration;

/**
 * TimeToLiveCacheStrategy is an interface that extends CacheStrategy and represents a data caching strategy
 * with time-to-live (TTL) capability. It allows setting and retrieving TTL for individual cache items, as well
 * as extending the expiry time of cache items.
 */
public interface TimeToLiveCacheStrategy<K, V> extends CacheStrategy<K, V> {

    /**
     * Sets the time-to-live (TTL) for the cache item with the specified key.
     *
     * @param key the key of the cache item
     * @param timeToLimit the duration of time after which the cache item will expire
     */
    void setTTL(K key, Duration timeToLimit);

    /**
     * Gets the time-to-live (TTL) for the cache item with the specified key.
     *
     * @param key the key of the cache item
     * @return the duration of time remaining for the cache item to expire, or null if the item does not have TTL set
     */
    Duration getTTL(K key);

    /**
     * Extends the expiry time of the cache item with the specified key by the given extension duration.
     * If the cache item does not have TTL set, this method will behave as a no-op.
     *
     * @param key the key of the cache item
     * @param extension the duration by which to extend the cache item's expiry time
     */
    void extendExpiry(K key, Duration extension);
}
