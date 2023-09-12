package com.github.msafriends.serviceproduct.moduleapi.service.product.event;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import com.github.msafriends.serviceproduct.modulecore.dto.product.ProductEvent;
import com.github.msafriends.serviceproduct.modulecore.repository.ProductRepository;
import com.github.msafriends.serviceproduct.modulecore.repository.ProductReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductEventListener {
    private final ProductRepository productRepository;
    private final ProductReviewRepository reviewRepository;

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void handleProductEvent(ProductEvent event){
        var product = productRepository.findByIdOrThrow(event.getProductId());
        Float averageRating = reviewRepository.getAverageRatingByProductId(event.getProductId());
        log.info("Average Rating of Product Id ({}), updated : {}", event.getProductId(), averageRating);
        product.updateBuySatisfy(averageRating);
    }
}
