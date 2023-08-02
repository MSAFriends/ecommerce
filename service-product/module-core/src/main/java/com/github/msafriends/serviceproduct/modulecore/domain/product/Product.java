package com.github.msafriends.serviceproduct.modulecore.domain.product;

import com.github.msafriends.serviceproduct.modulecore.domain.productimage.ProductImage;
import com.github.msafriends.serviceproduct.modulecore.domain.category.Category;
import com.github.msafriends.serviceproduct.modulecore.domain.review.ProductReview;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

import com.github.msafriends.modulecommon.base.BaseTimeEntity;
import com.github.msafriends.modulecommon.exception.ErrorCode;
import com.github.msafriends.modulecommon.exception.InvalidValueException;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Product extends BaseTimeEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductReview> productReviews = new ArrayList<>();
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> productImages = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    @Column(unique = true, nullable = false)
    private Long code;
    @Column(nullable = false)
    private String name;
    @Embedded
    private Price price;
    private int quantity;
    @Column(nullable = false)
    private String delivery;
    private int buySatisfy;
    @Embedded
    private Benefit benefit;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AgeLimit ageLimit;
    @Enumerated(EnumType.STRING)
    private Size size;
    @Column(nullable = false)
    private Long sellerId;

    @Builder
    public Product(Long code, String name, Price price, int quantity, String delivery, int buySatisfy, Benefit benefit,
        AgeLimit ageLimit, Size size, Long sellerId, Category category, List<ProductReview> productReviews,
        List<ProductImage> productImages) {
        validateProduct(sellerId, code, name, delivery, ageLimit, quantity);
        this.code = code;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.delivery = delivery;
        this.buySatisfy = buySatisfy;
        this.benefit = benefit;
        this.ageLimit = ageLimit;
        this.size = size;
        this.sellerId = sellerId;
        this.category = category;
        this.productReviews = productReviews;
        this.productImages = productImages;
    }

    public void assignCategory(Category category){
        this.category = category;
    }

    private void validateProduct(Long sellerId, Long code, String name, String delivery, AgeLimit ageLimit, int quantity) {
        validateNotNull(sellerId, code, name, delivery, ageLimit);
        validateQuantity(quantity);
    }

     private void validateQuantity(int quantity) {
         if (isNegative(quantity)) {
             throw new InvalidValueException(ErrorCode.INVALID_QUANTITY_ERROR, "수량은 0보다 커야 합니다.");
         }
     }

     private boolean isNegative(int value){
       return value < 0;
     }

     private void validateNotNull(Long sellerId, Long code, String name,  String delivery, AgeLimit ageLimit) {
        Assert.notNull(sellerId, "sellerId must not be null");
        Assert.notNull(code, "code must not be null");
        Assert.notNull(name, "name must not be null");
        Assert.notNull(delivery, "delivery must not be null");
        Assert.notNull(ageLimit, "isMinor must not be null");
    }
}
