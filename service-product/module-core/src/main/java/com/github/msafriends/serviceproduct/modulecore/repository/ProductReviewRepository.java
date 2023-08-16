package com.github.msafriends.serviceproduct.modulecore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.github.msafriends.serviceproduct.modulecore.domain.review.ProductReview;
import com.github.msafriends.serviceproduct.modulecore.exception.EntityNotFoundException;
import com.github.msafriends.serviceproduct.modulecore.exception.ErrorCode;

public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {

    default ProductReview findByIdOrThrow(Long id){
        return findById(id)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.REVIEW_NOT_EXIST, id));
    }

    @Query("SELECT r FROM ProductReview r WHERE r.product = :productId")
    List<ProductReview> findAllByProductId(Long productId);

    List<ProductReview> findAllByMemberId(Long memberId);
}
