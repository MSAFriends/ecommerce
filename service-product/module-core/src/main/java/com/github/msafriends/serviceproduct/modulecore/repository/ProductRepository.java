package com.github.msafriends.serviceproduct.modulecore.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import com.github.msafriends.serviceproduct.modulecore.domain.product.Product;
import com.github.msafriends.serviceproduct.modulecore.exception.EntityNotFoundException;
import com.github.msafriends.serviceproduct.modulecore.exception.ErrorCode;

import jakarta.persistence.LockModeType;

public interface ProductRepository extends JpaRepository<Product, Long> {
    default Product findByIdOrThrow(Long id){
        return findById(id)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.PRODUCT_NOT_EXIST, id));
    }

    Page<Product> findProductsBySellerId(Long sellerId, Pageable pageable);

    List<Product> findProductsByIdIn(List<Long> ids);
  
    @Lock(LockModeType.OPTIMISTIC)
    @Query("SELECT p FROM Product p WHERE p.id in :ids")
    List<Product> findProductsByIdInWithOptimisticLock(List<Long> ids);

    Page<Product> findProductByCategoryId(Long categoryId, Pageable pageable);
}