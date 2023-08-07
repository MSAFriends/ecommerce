package com.github.msafriends.serviceproduct.moduleapi.service.facade;

import java.util.List;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Component;

import com.github.msafriends.serviceproduct.moduleapi.dto.UpdateStockRequest;
import com.github.msafriends.serviceproduct.moduleapi.service.OptimisticLockProductService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OptimisticLockProductFacade {
    private static final int RETRY_WAIT_TIME = 50;

    private final OptimisticLockProductService productService;

    public void updateStocks(List<UpdateStockRequest> updateStockRequests) {
        while(true){
            try {
                productService.updateStocks(updateStockRequests);
                break;
            }catch (OptimisticLockingFailureException ex){
                handleOptimisticLockingFailure();
            }
        }
    }

    private void handleOptimisticLockingFailure() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
           Thread.currentThread().interrupt();
        }
    }
}
