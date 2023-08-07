package com.github.msafriends.modulecommon.lock;

import jakarta.persistence.OptimisticLockException;

public interface OptimisticLockTemplate {
    int DEFAULT_RETRY_WAIT_TIME = 50;

    default <T> T execute(OperationCallback<T> callback) {
        return execute(callback, DEFAULT_RETRY_WAIT_TIME);
    }

    default <T> T execute(OperationCallback<T> callback, int retryWaitTime) {
        while (true) {
            try {
                return callback.execute();
            } catch (OptimisticLockException e) {
                handleOptimisticLockingFailure(retryWaitTime);
            }
        }
    }

    private void handleOptimisticLockingFailure(int retryWaitTime) {
        try {
            Thread.sleep(retryWaitTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
