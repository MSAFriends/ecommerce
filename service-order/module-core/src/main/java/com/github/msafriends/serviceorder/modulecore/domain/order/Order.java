package com.github.msafriends.serviceorder.modulecore.domain.order;

import com.github.msafriends.serviceorder.modulecore.base.BaseTimeEntity;
import com.github.msafriends.serviceorder.modulecore.domain.coupon.strategy.PriceCalculator;
import com.github.msafriends.serviceorder.modulecore.domain.product.Product;
import com.github.msafriends.serviceorder.modulecore.dto.CouponResponse;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Embedded
    private Recipient recipient;

    private String request;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Builder
    public Order(Long memberId, String request, Recipient recipient, OrderStatus status, List<Product> products) {
        validateOrder(memberId, recipient, status, products);

        this.memberId = memberId;
        this.request = request;
        this.recipient = recipient;
        this.status = status;
        this.orderItems = getOrderItems(products);
    }

    private List<OrderItem> getOrderItems(List<Product> products) {
        return products.stream()
                .map(product -> OrderItem.builder()
                        .order(this)
                        .product(product)
                        .build())
                .toList();
    }

    public int getTotalPrice() {
        var priceCalculator = new PriceCalculator(orderItems, Collections.emptyList());
        return priceCalculator.calculateTotalPrice();
    }

    public int getDiscountedPrice(List<CouponResponse> coupons) {
        var priceCalculator = new PriceCalculator(orderItems, coupons);
        return priceCalculator.calculateDiscountedPrice();
    }

    private void validateOrder(Long memberId, Recipient recipient, OrderStatus status, List<Product> products) {
        validateNotNull(memberId, recipient, status, products);
        validateIfEmpty(products);
    }

    private void validateIfEmpty(List<Product> products) {
        Assert.notEmpty(products, "products must not be empty");
    }

    private void validateNotNull(Long memberId, Recipient recipient, OrderStatus status, List<Product> products) {
        Assert.notNull(status, "status must not be null");
        Assert.notNull(memberId, "memberId must not be null");
        Assert.notNull(recipient, "recipient must not be null");
        Assert.notNull(products, "products must not be null");
    }
}
