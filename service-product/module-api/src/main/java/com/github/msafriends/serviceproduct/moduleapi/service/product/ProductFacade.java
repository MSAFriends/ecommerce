package com.github.msafriends.serviceproduct.moduleapi.service.product;

import java.util.List;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.github.msafriends.serviceproduct.modulecore.domain.product.Product;
import com.github.msafriends.serviceproduct.modulecore.dto.product.UpdateStockRequest;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductFacade {
    private static final int RETRY_WAIT_TIME = 50;

    private final ProductService productService;

    public Long registerProduct(Long categoryId, Product product){
        return productService.registerProduct(categoryId, product);
    }

    public Long registerProduct(Product product){
        return productService.registerProduct(product);
    }
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

    public Page<Product> readProductsBySellerId(Long sellerId, Pageable pageable){
        return productService.readProductsBySellerId(sellerId, pageable);
    }

    public Page<Product> readProductsByCategoryId(Long categoryId, Pageable pageable){
        return productService.readProductsByCategoryId(categoryId, pageable);
    }
}