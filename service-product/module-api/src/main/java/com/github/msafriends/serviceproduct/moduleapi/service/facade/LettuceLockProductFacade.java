package com.github.msafriends.serviceproduct.moduleapi.service.facade;

import java.util.List;

import org.springframework.stereotype.Component;

import com.github.msafriends.serviceproduct.moduleapi.dto.UpdateStockRequest;
import com.github.msafriends.serviceproduct.moduleapi.service.DefaultProductServiceImpl;
import com.github.msafriends.serviceproduct.moduleapi.service.RedisLockRepository;

@Component
public class LettuceLockProductFacade {
    private final RedisLockRepository lockRepository;
    private final DefaultProductServiceImpl productService;

    public LettuceLockProductFacade(RedisLockRepository lockRepository, DefaultProductServiceImpl productService) {
        this.lockRepository = lockRepository;
        this.productService = productService;
    }

    public void updateStocks(List<UpdateStockRequest> updateStockRequests){
        updateStockRequests.forEach(this::updateStock);
    }

    private void updateStock(final UpdateStockRequest updateStockRequest) {

        while(!lockRepository.lock(updateStockRequest.getProductId())){
            try{
                Thread.sleep(100);
            } catch (InterruptedException e) {
               Thread.currentThread().interrupt();
            }
        }
        try {
            productService.updateEachStock(updateStockRequest);
        } finally {
            lockRepository.unlock(updateStockRequest.getProductId());
        }
    }
}
