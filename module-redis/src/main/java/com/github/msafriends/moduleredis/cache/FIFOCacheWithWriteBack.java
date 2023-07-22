package com.github.msafriends.moduleredis.cache;

import com.github.msafriends.moduleredis.cache.persistence.WriteBackDataPersistenceStrategy;
import com.github.msafriends.moduleredis.cache.strategy.FIFOCacheStrategy;

public class FIFOCacheWithWriteBack<K, V> implements FIFOCacheStrategy<K, V>, WriteBackDataPersistenceStrategy<K, V> {
}
