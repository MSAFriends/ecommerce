package com.github.msafriends.moduleredis.cache;

public interface LRUCacheStrategy<KEY, VALUE> extends EvictionCacheStrategy<KEY, VALUE> {
    void moveToMRU(KEY key);
    void moveToLRU(KEY key);
    KEY getMostRecentlyUsedKey();
    KEY getLeastRecentlyUsedKey();
}
