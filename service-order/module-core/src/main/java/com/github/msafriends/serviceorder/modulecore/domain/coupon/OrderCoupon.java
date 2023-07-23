package com.github.msafriends.serviceorder.modulecore.domain.coupon;

import com.github.msafriends.serviceorder.modulecore.domain.order.Order;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Entity
@Getter
@Table(name = "order_coupons")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_coupon_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CouponDiscountType discountType;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer discountValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Builder
    public OrderCoupon(CouponDiscountType discountType, String name, Integer discountValue, Order order) {
        validateCoupon(discountType, name, discountValue, order);

        this.discountType = discountType;
        this.name = name;
        this.discountValue = discountValue;
        this.order = order;
    }

    private void validateCoupon(CouponDiscountType discountType, String name, Integer discountValue, Order order) {
        validateNotNull(discountType, name, discountValue, order);
    }

    private void validateNotNull(CouponDiscountType discountType, String name, Integer discountValue, Order order) {
        Assert.notNull(discountType, "discountType must not be null");
        Assert.notNull(name, "name must not be null");
        Assert.notNull(discountValue, "discountValue must not be null");
        Assert.notNull(order, "order must not be null");
    }

    public int applyDiscount(int price) {
        return Math.max(0, discountType.strategy().apply(price, discountValue));
    }
}
