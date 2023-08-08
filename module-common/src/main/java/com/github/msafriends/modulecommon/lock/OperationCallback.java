package com.github.msafriends.modulecommon.lock;

@FunctionalInterface
public interface OperationCallback<T> {
    T execute();
}
