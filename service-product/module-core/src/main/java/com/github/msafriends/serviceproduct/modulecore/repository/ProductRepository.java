package com.github.msafriends.serviceproduct.modulecore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.msafriends.modulecommon.exception.EntityNotFoundException;
import com.github.msafriends.modulecommon.exception.ErrorCode;
import com.github.msafriends.serviceproduct.modulecore.domain.product.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    default Product findByIdOrThrow(Long id){
        return findById(id)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.PRODUCT_NOT_EXIST, id));
    }
    public List<Product> findProductsByIdIn(List<Long> ids);
}
