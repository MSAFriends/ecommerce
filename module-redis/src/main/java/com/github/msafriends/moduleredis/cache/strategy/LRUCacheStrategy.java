package com.github.msafriends.moduleredis.cache.strategy;

/**
 * Interface representing Least Recently Used (LRU) caching strategy.
 * Extends EvictionCacheStrategy and allows defining methods specific to LRU strategy.
 */
public interface LRUCacheStrategy<K, V> extends EvictionCacheStrategy<K, V> {
    /**
     * Moves the given key to the Most Recently Used (MRU) position.
     * @param key The key to move to the MRU position.
     */
    void moveToMRU(K key);

    /**
     * Moves the given key to the Least Recently Used (LRU) position.
     * @param key The key to move to the LRU position.
     */
    void moveToLRU(K key);

    /**
     * Retrieves the most recently used key in the cache.
     * @return The key of the most recently used item.
     */
    K getMostRecentlyUsedKey();

    /**
     * Retrieves the least recently used key in the cache.
     * @return The key of the least recently used item.
     */
    K getLeastRecentlyUsedKey();
}
