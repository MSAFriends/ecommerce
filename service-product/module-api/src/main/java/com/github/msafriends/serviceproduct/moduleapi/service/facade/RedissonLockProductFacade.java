package com.github.msafriends.serviceproduct.moduleapi.service.facade;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.github.msafriends.serviceproduct.moduleapi.dto.UpdateStockRequest;
import com.github.msafriends.serviceproduct.moduleapi.service.ProductService;

@Component
public class RedissonLockProductFacade {
    private final RedissonClient redissonClient;
    private final ProductService productService;

    public RedissonLockProductFacade(RedissonClient redissonClient, @Qualifier("defaultProductService") ProductService productService) {
        this.redissonClient = redissonClient;
        this.productService = productService;
    }

    public void updateStocks(List<UpdateStockRequest> updateStockRequests) {
        RLock lock = redissonClient.getLock("orderedProducts");
        try{
            boolean available = lock.tryLock(15, 1, TimeUnit.SECONDS);
            if(available){
                productService.updateStocks(updateStockRequests);
            }
        }catch (InterruptedException ex){
            Thread.currentThread().interrupt();
        }finally {
            lock.unlock();
        }
    }
}
