package com.github.msafriends.serviceproduct.modulecore.repository;

import com.github.msafriends.modulecommon.exception.EntityNotFoundException;
import com.github.msafriends.modulecommon.exception.ErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;

import com.github.msafriends.serviceproduct.modulecore.domain.product.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    default Product getProductByIdOrThrow(Long productId) {
        return findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.PRODUCT_NOT_EXIST));
    }
}
