package com.github.msafriends.moduleredis.cache.persistence;

/**
 * ReadThroughDataPersistenceStrategy is a data persistence strategy that allows retrieving the value
 * with the specified key from the cache or from other underlying data sources if not found in the cache.
 * This interface provides the flexibility to cache data using a specific key or access data from
 * different database tables or data structures as needed. */
public interface ReadThroughDataPersistenceStrategy<K, V> extends DataPersistenceStrategy<K, V> {

    /**
     * Gets the value with the specified key from the cache. If the value is not found in the cache,
     * this method reads the value from the underlying data source and stores it in the cache.
     *
     * @param key the key used for caching data or for other database tables or data structures
     * @return the value to be cached or persisted
     */
    V getOrRead(K key);
}
