package com.github.msafriends.moduleredis.cache.persistence;

/**
 * ReadWriteThroughDataPersistenceStrategy is an interface that extends WriteThroughDataPersistenceStrategy
 * and represents a data persistence strategy that allows both read and write operations.
 * It combines the functionalities of reading data from the cache or the underlying data source,
 * as well as writing data to the cache and the data source in a single interface.
 */
public interface ReadWriteThroughDataPersistenceStrategy <K, V> extends WriteThroughDataPersistenceStrategy<K ,V> {
    /**
     * Gets the value with the specified key from the cache. If the value is not found in the cache,
     * this method reads the value from the underlying data source, stores it in the cache, and then
     * puts it into the cache.
     *
     * @param key the key used for caching data or for other database tables or data structures
     * @return the value to be cached or persisted
     */
    V getOrReadAndPut(K key);
}
