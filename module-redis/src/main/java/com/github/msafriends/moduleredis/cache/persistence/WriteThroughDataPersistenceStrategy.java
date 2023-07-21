package com.github.msafriends.moduleredis.cache.persistence;

/**
 * WriteThroughDataPersistenceStrategy is an interface that extends DataPersistenceStrategy and represents a
 * data persistence strategy that allows both putting data into the cache and writing it to the underlying
 * data source in a single operation. When data is put into the cache using this strategy, it is immediately
 * written to the data source to ensure synchronization between the cache and the underlying data.
 */
public interface WriteThroughDataPersistenceStrategy<K, V> extends DataPersistenceStrategy<K, V> {

    /**
     * Puts the specified key-value pair into the cache and writes it to the underlying data source.
     * The data will be immediately persisted to the data source to ensure synchronization between
     * the cache and the underlying data.
     *
     * @param key the key of the cache item
     * @param value the value of the cache item
     */
    void putAndWrite(K key, V value);
}
