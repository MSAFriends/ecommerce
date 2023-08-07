package com.github.msafriends.serviceproduct.moduleapi.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.msafriends.modulecommon.exception.EntityNotFoundException;
import com.github.msafriends.modulecommon.exception.ErrorCode;
import com.github.msafriends.serviceproduct.moduleapi.dto.UpdateStockRequest;
import com.github.msafriends.serviceproduct.modulecore.domain.category.Category;
import com.github.msafriends.serviceproduct.modulecore.domain.product.Product;
import com.github.msafriends.serviceproduct.modulecore.repository.CategoryRepository;
import com.github.msafriends.serviceproduct.modulecore.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@Qualifier("optimisticLockProductService")
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OptimisticLockProductService implements ProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public Long registerProduct(Long categoryId, Product product){
        if(categoryId != null){
            Category category = categoryRepository.findByIdOrThrow(categoryId);
            product.assignCategory(category);
        }
        return productRepository.save(product).getId();
    }

    @Transactional
    public Long registerProduct(Product product){
        return productRepository.save(product).getId();
    }


    @Transactional
    @Override
    public void updateStocks(List<UpdateStockRequest> updateStockRequests) {
        List<Long> orderedIds = updateStockRequests.stream()
            .map(UpdateStockRequest::getProductId)
            .toList();
        Map<Long, Integer> requestMap = updateStockRequests.stream()
            .collect(Collectors.toMap(UpdateStockRequest::getProductId, UpdateStockRequest::getQuantity));
        List<Product> foundProducts = productRepository.findProductsByIdInWithOptimisticLock(orderedIds);
        if(orderedIds.size() != foundProducts.size())
            throw new EntityNotFoundException(ErrorCode.INVALID_ORDER_ERROR, "유효하지 않은 상품 id가 주문에 포함되어 있습니다.");
        foundProducts.forEach(product -> product.updateStockQuantity(requestMap.get(product.getId())));
    }

    @Override
    public List<Product> readProductsBySellerId(Long sellerId) {
        return null;
    }

    @Override
    public List<Product> readProductByCategoryId(Long categoryId) {
        return null;
    }
}
