package com.github.msafriends.moduleredis.cache.strategy;

/**
 * Interface representing Least Frequently Used (LFU) caching strategy.
 * Extends EvictionCacheStrategy and allows defining methods specific to LFU strategy.
 */
public interface LFUCacheStrategy<K, V> extends EvictionCacheStrategy<K, V>{
    /**
     * Increases the frequency of usage for the given key.
     * @param key The key for which to increase the usage frequency.
     */
    void increaseFrequency(K key);

    /**
     * Decreases the frequency of usage for the given key.
     * @param key The key for which to decrease the usage frequency.
     */
    void decreaseFrequency(K key);

    /**
     * Retrieves the current usage frequency for the given key.
     * @param key The key for which to retrieve the usage frequency.
     * @return The current usage frequency of the key.
     */
    int getFrequency(K key);

    /**
     * Returns the minimum usage frequency among all keys managed by LFU strategy.
     * @return The minimum usage frequency.
     */
    int getMinFrequency();

    /**
     * Returns the maximum usage frequency among all keys managed by LFU strategy.
     * @return The maximum usage frequency.
     */
    int getMaxFrequency();

    /**
     * Retrieves the current usage frequency for the given key.
     * @param key The key for which to retrieve the usage frequency.
     * @return The current usage frequency of the key.
     */
    int getCurrentFrequency(K key);
}
