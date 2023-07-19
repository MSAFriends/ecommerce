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
@Table(name = "coupons")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CouponDiscountType discountType;

    @Column(nullable = false)
    private Integer discountValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Builder
    public Coupon(Order order, CouponDiscountType discountType, Integer discountValue) {
        validateCoupon(discountType, discountValue, order);

        this.discountType = discountType;
        this.discountValue = discountValue;
        this.order = order;
    }

    private void validateCoupon(CouponDiscountType discountType, Integer discountValue, Order order) {
        validateNotNull(discountType, discountValue, order);
    }

    private void validateNotNull(CouponDiscountType discountType, Integer discountValue, Order order) {
        Assert.notNull(discountType, "discountType must not be null");
        Assert.notNull(discountValue, "discountValue must not be null");
        Assert.notNull(order, "order must not be null");
    }

    public int applyDiscount(int price) {
        return Math.max(0, discountType.strategy().apply(price, discountValue));
    }
}
