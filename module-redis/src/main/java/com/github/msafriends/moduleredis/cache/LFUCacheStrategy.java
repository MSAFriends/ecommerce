package com.github.msafriends.moduleredis.cache;

public interface LFUCacheStrategy<KEY, VALUE> extends EvictionCacheStrategy<KEY, VALUE>{
    void increaseFrequency(KEY key);
    void decreaseFrequency(KEY key);
    int getFrequency(KEY key);
    int getMinFrequency();
    int getMaxFrequency();
    int getCurrentFrequency(KEY key);
}
