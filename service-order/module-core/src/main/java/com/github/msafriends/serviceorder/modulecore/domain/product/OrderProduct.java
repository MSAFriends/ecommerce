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
public class OrderProduct {

    @Column(name = "product_id", nullable = false)
    private Long id;

    @Column(name = "product_name", nullable = false)
    private String name;

    @Column(name = "product_quantity", nullable = false)
    private int quantity;

    @Column(name = "product_price", nullable = false)
    private int price;

    @Builder
    public OrderProduct(Long id, String name, int quantity, int price) {
        validateProduct(id, name, quantity, price);

        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public void addQuantity(int quantity) {
        validateQuantity(this.quantity + quantity);
        this.quantity += quantity;
    }

    private void validateProduct(Long id, String name, int quantity, int price) {
        validateNotNull(id, name);
        validateQuantity(quantity);
        validatePrice(price);
    }

    private void validateQuantity(Integer quantity) {
        Assert.isTrue(quantity > 0, "quantity must be positive");
    }

    private void validatePrice(Integer price) {
        Assert.isTrue(price >= 0, "price must not be negative");
    }

    private void validateNotNull(Long id, String name) {
        Assert.notNull(id, "productId must not be null");
        Assert.notNull(name, "name must not be null");
    }
}
