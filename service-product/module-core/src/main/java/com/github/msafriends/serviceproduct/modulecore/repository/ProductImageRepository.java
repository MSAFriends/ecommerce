package com.github.msafriends.serviceproduct.modulecore.repository;

import com.github.msafriends.modulecommon.exception.EntityNotFoundException;
import com.github.msafriends.modulecommon.exception.ErrorCode;
import com.github.msafriends.serviceproduct.modulecore.domain.productimage.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    List<ProductImage> findAllByProductId(Long productId);

    default ProductImage findByIdOrThrow(Long productId) {
        return findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.PRODUCT_IMAGE_NOT_EXIST));
    }
}
