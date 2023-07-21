package com.github.msafriends.moduleredis.cache.persistence;

/**
 * WriteBackDataPersistenceStrategy is an interface that extends DataPersistenceStrategy and represents a
 * data persistence strategy that allows writing data to the cache without immediately writing it to the
 * underlying data source. It provides methods for putting data into the cache without writing it to the
 * data source, as well as for writing all the pending changes in the cache to the data source.
 */
public interface WriteBackDataPersistenceStrategy<K, V> extends DataPersistenceStrategy<K, V> {
    /**
     * Puts the specified key-value pair into the cache without immediately writing it to the data source.
     * The data will be persisted to the data source at a later time as determined by the implementation.
     *
     * @param key the key of the cache item
     * @param value the value of the cache item
     */
    void putWithoutWrite(K key, V value);

    /**
     * Writes all the pending changes in the cache to the underlying data source.
     * The implementation of this method will ensure that any changes made in the cache
     * are synchronized and persisted to the data source.
     *
     * This method should be called at appropriate times to ensure that any changes made in the cache
     * are eventually persisted to the data source, as the actual writes to the data source may be deferred.
     */
    void writeAll();
}
