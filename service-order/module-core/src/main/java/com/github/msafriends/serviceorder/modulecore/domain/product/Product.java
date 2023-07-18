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

    @Column(nullable = false)
    private Long productId;

    @Column(name = "product_quantity", nullable = false)
    private Integer quantity;

    @Column(name = "product_price", nullable = false)
    private Integer price;

    @Builder
    public Product(Long productId, Integer quantity, Integer price) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;

        validateProduct();
    }

    private void validateProduct() {
        validateNotNull();
    }

    private void validateNotNull() {
        Assert.notNull(productId, "productId must not be null");
        Assert.notNull(quantity, "quantity must not be null");
        Assert.notNull(price, "price must not be null");
    }
}
