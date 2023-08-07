package com.github.msafriends.modulecommon.lock;

@FunctionalInterface
public interface OptimisticLockCallback<T> {
    T doInLock();
}
