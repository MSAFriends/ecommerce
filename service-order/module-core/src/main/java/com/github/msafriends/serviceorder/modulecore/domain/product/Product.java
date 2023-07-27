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

    @Column(name = "product_name", nullable = false)
    private String name;

    @Column(name = "product_quantity", nullable = false)
    private Integer quantity;

    @Column(name = "product_price", nullable = false)
    private Integer price;

    @Builder
    public Product(Long id, String name, Integer quantity, Integer price) {
        validateProduct(id, name, quantity, price);

        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public void addQuantity(Integer quantity) {
        validateQuantity(this.quantity + quantity);
        this.quantity += quantity;
    }

    private void validateProduct(Long id, String name, Integer quantity, Integer price) {
        validateNotNull(id, name, quantity, price);
        validateQuantity(quantity);
        validatePrice(price);
    }

    private void validateQuantity(Integer quantity) {
        Assert.isTrue(quantity > 0, "quantity must be positive");
    }

    private void validatePrice(Integer price) {
        Assert.isTrue(price >= 0, "price must not be negative");
    }

    private void validateNotNull(Long id, String name, Integer quantity, Integer price) {
        Assert.notNull(id, "productId must not be null");
        Assert.notNull(name, "name must not be null");
        Assert.notNull(quantity, "quantity must not be null");
        Assert.notNull(price, "price must not be null");
    }
}
