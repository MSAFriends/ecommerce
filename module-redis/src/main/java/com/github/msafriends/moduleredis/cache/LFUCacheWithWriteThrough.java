package com.github.msafriends.moduleredis.cache;

import com.github.msafriends.moduleredis.cache.persistence.WriteThroughDataPersistenceStrategy;
import com.github.msafriends.moduleredis.cache.strategy.LFUCacheStrategy;

public class LFUCacheWithWriteThrough<K, V> implements LFUCacheStrategy<K, V>, WriteThroughDataPersistenceStrategy<K, V> {
}
