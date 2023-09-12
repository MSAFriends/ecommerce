package com.github.msafriends.serviceproduct.moduleapi.service;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.msafriends.serviceproduct.modulecore.dto.product.ProductEvent;
import com.github.msafriends.serviceproduct.modulecore.dto.productreview.ProductReviewRequest;
import com.github.msafriends.serviceproduct.modulecore.dto.productreview.ReviewUpdateRequest;
import com.github.msafriends.serviceproduct.modulecore.domain.product.Product;
import com.github.msafriends.serviceproduct.modulecore.domain.review.ProductReview;
import com.github.msafriends.serviceproduct.modulecore.repository.ProductRepository;
import com.github.msafriends.serviceproduct.modulecore.repository.ProductReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductReviewService {
    private final ProductReviewRepository productReviewRepository;
    private final ProductRepository productRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public Long save(Long memberId, Long productId, ProductReviewRequest request){
        Product product = productRepository.findByIdOrThrow(productId);
        eventPublisher.publishEvent(new ProductEvent(productId));
        ProductReview productReview = request.toProductReview(product, memberId);
        return productReviewRepository.save(productReview).getId();
    }

    public List<ProductReview> findReviewByProductId(Long productId){
        return productReviewRepository.findAllByProductId(productId);
    }

    public List<ProductReview> findReviewByMemberId(Long memberId){
        return productReviewRepository.findAllByMemberId(memberId);
    }

    @Transactional
    public void updateReview(Long reviewId, ReviewUpdateRequest request){
        ProductReview foundReview = productReviewRepository.findByIdOrThrow(reviewId);
        foundReview.update(request.getRating(), request.getTitle(), request.getContent());
    }
}
