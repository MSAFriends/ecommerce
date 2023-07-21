package com.github.msafriends.moduleredis.cache;

import org.springframework.stereotype.Indexed;

@Indexed
public interface CacheStrategy <KEY, VALUE> {
    VALUE get(KEY key);
    void put(KEY key, VALUE value);
    void remove(KEY key);
    void clear();
    int getSize();
    boolean contains(KEY key);
}
