package com.github.msafriends.serviceorder.modulecore.domain.order;

import com.github.msafriends.serviceorder.modulecore.base.BaseTimeEntity;
import com.github.msafriends.serviceorder.modulecore.domain.coupon.OrderCoupon;
import com.github.msafriends.serviceorder.modulecore.domain.coupon.strategy.PriceCalculator;
import com.github.msafriends.serviceorder.modulecore.domain.product.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.util.ArrayList;
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

    @Column(nullable = false)
    private Integer totalPrice;

    @Column(nullable = false)
    private Integer discountedPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OrderCoupon> coupons = new ArrayList<>();

    @Builder
    public Order(Long memberId, String request, Recipient recipient, OrderStatus status, List<Product> products) {
        validateOrder(memberId, recipient, status, products, coupons);

        this.memberId = memberId;
        this.request = request;
        this.recipient = recipient;
        this.status = status;
        this.orderItems = generateOrderItems(products);

        recalculatePrice();
    }

    public void addCoupon(OrderCoupon coupon) {
        coupons.add(coupon);
        recalculatePrice();
    }

    public void addCoupons(List<OrderCoupon> coupons) {
        this.coupons.addAll(coupons);
        recalculatePrice();
    }

    private List<OrderItem> generateOrderItems(List<Product> products) {
        return products.stream()
                .map(product -> OrderItem.builder()
                        .order(this)
                        .product(product)
                        .build())
                .toList();
    }

    private void recalculatePrice() {
        var priceCalculator = new PriceCalculator(orderItems, coupons);
        this.totalPrice = priceCalculator.calculateTotalPrice();
        this.discountedPrice = priceCalculator.calculateDiscountedPrice();
    }

    private void validateOrder(Long memberId, Recipient recipient, OrderStatus status, List<Product> products, List<OrderCoupon> coupons) {
        validateNotNull(memberId, recipient, status, products, coupons);
        validateNotEmpty(products);
    }

    private void validateNotEmpty(List<Product> products) {
        Assert.notEmpty(products, "products must not be empty");
    }

    private void validateNotNull(Long memberId, Recipient recipient, OrderStatus status, List<Product> products, List<OrderCoupon> coupons) {
        Assert.notNull(status, "status must not be null");
        Assert.notNull(memberId, "memberId must not be null");
        Assert.notNull(recipient, "recipient must not be null");
        Assert.notNull(products, "products must not be null");
        Assert.notNull(coupons, "coupons must not be null");
    }
}
