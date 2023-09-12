package com.github.msafriends.serviceproduct.modulecore.dto.productreview;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import com.github.msafriends.serviceproduct.modulecore.domain.product.Product;
import com.github.msafriends.serviceproduct.modulecore.domain.review.ProductReview;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductReviewRequest {
    private static final int MIN_TITLE_LENGTH = 5;
    private static final int MAX_TITLE_LENGTH = 50;
    private static final int MAX_CONTENT_LENGTH = 500;
    private static final int MAX_RATING_VALUE = 5;
    @NotNull
    private Long productId;
    @NotNull
    @Range(min = 0, max = MAX_RATING_VALUE)
    private Integer rating;
    @NotBlank
    @Length(min = MIN_TITLE_LENGTH, max = MAX_TITLE_LENGTH)
    private String title;
    @NotBlank
    @Length(max = MAX_CONTENT_LENGTH)
    private String content;

    @Builder
    public ProductReviewRequest(Long productId, Integer rating, String title, String content) {
        this.productId = productId;
        this.rating = rating;
        this.title = title;
        this.content = content;
    }

    public ProductReview toProductReview(Product product, Long memberId){
        return ProductReview.builder()
            .title(title)
            .rating(rating)
            .content(content)
            .product(product)
            .memberId(memberId)
            .build();
    }
}
