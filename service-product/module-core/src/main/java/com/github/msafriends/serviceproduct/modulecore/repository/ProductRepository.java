package com.github.msafriends.serviceproduct.modulecore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.msafriends.serviceproduct.modulecore.domain.product.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    default Product getProductByIdOrThrow(Long productId) {
        return findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
    }
}
