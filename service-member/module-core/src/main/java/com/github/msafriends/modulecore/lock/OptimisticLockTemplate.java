package com.github.msafriends.modulecore.lock;

import java.util.concurrent.Callable;

import jakarta.persistence.OptimisticLockException;

public interface OptimisticLockTemplate {
    int DEFAULT_RETRY_WAIT_TIME = 50;

    static <T> T execute(Callable<T> callback) {
        return execute(callback, DEFAULT_RETRY_WAIT_TIME);
    }

    static <T> T execute(Callable<T> callback, int retryWaitTime) {
        while (true) {
            try {
                return callback.call();
            } catch (OptimisticLockException e) {
                handleOptimisticLockingFailure(retryWaitTime);
            } catch (Exception e) {
                throw new RuntimeException("Unexpected exception", e);
            }
        }
    }

    private static void handleOptimisticLockingFailure(int retryWaitTime) {
        try {
            Thread.sleep(retryWaitTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
