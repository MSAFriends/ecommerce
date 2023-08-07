package com.github.msafriends.serviceproduct.moduleapi.service;

import java.util.List;

import com.github.msafriends.serviceproduct.moduleapi.dto.UpdateStockRequest;
import com.github.msafriends.serviceproduct.modulecore.domain.product.Product;

public interface ProductService {
    Long registerProduct(Long categoryId, Product product);
    Long registerProduct(Product product);
    void updateStocks(List<UpdateStockRequest> updateStockRequests);
    List<Product>readProductsBySellerId(Long sellerId);
    List<Product>readProductByCategoryId(Long categoryId);
}
