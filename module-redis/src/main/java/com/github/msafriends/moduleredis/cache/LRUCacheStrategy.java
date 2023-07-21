package com.github.msafriends.moduleredis.cache;

/**
 * Interface representing Least Recently Used (LRU) caching strategy.
 * Extends EvictionCacheStrategy and allows defining methods specific to LRU strategy.
 */
public interface LRUCacheStrategy<KEY, VALUE> extends EvictionCacheStrategy<KEY, VALUE> {
    /**
     * Moves the given key to the Most Recently Used (MRU) position.
     * @param key The key to move to the MRU position.
     */
    void moveToMRU(KEY key);

    /**
     * Moves the given key to the Least Recently Used (LRU) position.
     * @param key The key to move to the LRU position.
     */
    void moveToLRU(KEY key);

    /**
     * Retrieves the most recently used key in the cache.
     * @return The key of the most recently used item.
     */
    KEY getMostRecentlyUsedKey();

    /**
     * Retrieves the least recently used key in the cache.
     * @return The key of the least recently used item.
     */
    KEY getLeastRecentlyUsedKey();
}
