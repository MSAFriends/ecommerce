package com.github.msafriends.serviceorder.modulecore.domain.product;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Column(name = "product_id", nullable = false)
    private Long id;

    @Column(name = "product_quantity", nullable = false)
    private Integer quantity;

    @Column(name = "product_price", nullable = false)
    private Integer price;

    @Builder
    public Product(Long id, Integer quantity, Integer price) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;

        validateProduct();
    }

    private void validateProduct() {
        validateNotNull();
        validateNotNegative();
    }

    private void validateNotNegative() {
        Assert.isTrue(quantity >= 0, "quantity must not be negative");
        Assert.isTrue(price >= 0, "price must not be negative");
    }

    private void validateNotNull() {
        Assert.notNull(id, "productId must not be null");
        Assert.notNull(quantity, "quantity must not be null");
        Assert.notNull(price, "price must not be null");
    }
}
