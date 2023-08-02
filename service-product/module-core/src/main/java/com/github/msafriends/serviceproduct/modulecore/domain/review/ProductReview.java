package com.github.msafriends.serviceproduct.modulecore.domain.review;


import com.github.msafriends.serviceproduct.modulecore.domain.product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.util.Assert;

import static lombok.AccessLevel.*;

import com.github.msafriends.modulecommon.base.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@DiscriminatorValue("PRODUCT")
public class ProductReview extends BaseTimeEntity {
    private static final int MAX_TITLE_LENGTH = 50;
    private static final int MAX_CONTENT_LENGTH = 500;
    private static final int MAX_RATING_VALUE = 5;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private Long id;
    private int rating;
    @Column(nullable = false, length = MAX_TITLE_LENGTH)
    private String title;
    @Column(nullable = false, length = MAX_CONTENT_LENGTH)
    private String content;
    @NotNull
    private Long memberId;

    @Builder
    public ProductReview(int rating, String title, String content, Product product, Long memberId) {
        validateProductReview(rating, product, memberId, title, content);
        this.rating = rating;
        this.title = title;
        this.content = content;
        this.product = product;
        this.memberId = memberId;
    }

    private void validateProductReview(int rating, Product product, Long memberId, String title, String content){
        validateNotNull(product, memberId, title, content);
        validateRating(rating);
    }

    private void validateRating(int rating) {
        Assert.isTrue(rating >= 0 && rating <= MAX_RATING_VALUE, "평점은 0 ~ 5점 사이로 입력해주세요.");
    }

    private void validateNotNull(Product product, Long memberId, String title, String content){
        Assert.notNull(product, "Product must not be null");
        Assert.notNull(memberId, "memberId must not be null");
        Assert.notNull(title, "title must not be null");
        Assert.notNull(content, "content must not be null");
    }
}
