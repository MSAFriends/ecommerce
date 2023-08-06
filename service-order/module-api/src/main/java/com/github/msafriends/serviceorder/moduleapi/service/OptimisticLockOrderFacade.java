package com.github.msafriends.serviceorder.moduleapi.service;

import com.github.msafriends.serviceorder.modulecore.dto.request.order.UpdateCartItemRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OptimisticLockOrderFacade {
    private static final int RETRY_WAIT_TIME = 50;

    private final OrderService orderService;

    public Long optimisticAddCartItemToOrder(Long memberId, UpdateCartItemRequest request) {
        while (true) {
            try {
                return orderService.addCartItemToOrder(memberId, request);
            } catch (OptimisticLockingFailureException ex) {
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
