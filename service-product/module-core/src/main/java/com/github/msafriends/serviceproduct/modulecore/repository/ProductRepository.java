package com.github.msafriends.serviceproduct.modulecore.repository;


import com.github.msafriends.modulecommon.exception.EntityNotFoundException;
import com.github.msafriends.modulecommon.exception.ErrorCode;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import com.github.msafriends.modulecommon.exception.EntityNotFoundException;
import com.github.msafriends.modulecommon.exception.ErrorCode;
import com.github.msafriends.serviceproduct.modulecore.domain.product.Product;

import jakarta.persistence.LockModeType;

public interface ProductRepository extends JpaRepository<Product, Long> {
    default Product findByIdOrThrow(Long id){
        return findById(id)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.PRODUCT_NOT_EXIST, id));
    }

    List<Product> findTop1000ProductsBySellerId(Long sellerId);

    List<Product> findProductsByIdIn(List<Long> ids);
  
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Product p WHERE p.id in :ids")
    List<Product> findProductsByIdInWithPessimisticLock(List<Long> ids);

    List<Product> findTop1000ProductByCategoryId(Long categoryId);

    @Lock(LockModeType.OPTIMISTIC)
    @Query("SELECT p FROM Product p WHERE p.id  in :ids")
    List<Product> findProductsByIdInWithOptimisticLock(List<Long> ids);
}