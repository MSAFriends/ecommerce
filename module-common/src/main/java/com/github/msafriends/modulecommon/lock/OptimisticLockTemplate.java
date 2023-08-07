package com.github.msafriends.modulecommon.lock;

import jakarta.persistence.OptimisticLockException;
import org.springframework.stereotype.Component;

@Component
public class OptimisticLockTemplate {
    private static final int RETRY_WAIT_TIME = 50;

    public <T> T execute(OptimisticLockCallback<T> callback) {
        while (true) {
            try {
                return callback.doInLock();
            } catch (OptimisticLockException e) {
                handleOptimisticLockingFailure();
            }
        }
    }

    private void handleOptimisticLockingFailure() {
        try {
            Thread.sleep(RETRY_WAIT_TIME);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
