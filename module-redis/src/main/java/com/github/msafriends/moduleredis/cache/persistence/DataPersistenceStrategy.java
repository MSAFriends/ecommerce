package com.github.msafriends.moduleredis.cache.persistence;

import org.springframework.stereotype.Indexed;

/**
 * DataPersistenceStrategy is an interface that represents a data persistence strategy for caching data
 * or for handling data related to other database tables or data structures.
 * It serves as a base interface for various data persistence strategies that can be used with the cache module.
 *
 * @param <K> the type of the key used for caching data or for other database tables or data structures
 * @param <V> the type of the value to be cached or persisted
 */
@Indexed
public interface DataPersistenceStrategy<K, V> {}
