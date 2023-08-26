package com.github.msafriends.serviceproduct.modulecore.dto.productreview;

import com.github.msafriends.serviceproduct.modulecore.domain.review.ProductReview;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductReviewResponse {
    private Long productId;
    private Integer rating;
    private String title;
    private String content;

    @Builder
    public ProductReviewResponse(Long productId, Integer rating, String title, String content) {
        this.productId = productId;
        this.rating = rating;
        this.title = title;
        this.content = content;
    }

    public static ProductReviewResponse from(ProductReview productReview){
        return ProductReviewResponse.builder()
            .productId(productReview.getId())
            .rating(productReview.getRating())
            .title(productReview.getTitle())
            .content(productReview.getContent())
            .build();
    }
}
