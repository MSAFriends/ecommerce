package com.github.msafriends.serviceorder.modulecore.domain.coupon.strategy;

import com.github.msafriends.serviceorder.modulecore.domain.coupon.Coupon;
import com.github.msafriends.serviceorder.modulecore.domain.coupon.CouponDiscountType;
import com.github.msafriends.serviceorder.modulecore.domain.order.OrderItem;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PriceCalculator {
    private final List<OrderItem> orderItems;
    private final List<Coupon> coupons;

    public int calculateTotalPrice() {
        return orderItems.stream()
                .mapToInt(item -> item.getProduct().getPrice() * item.getProduct().getQuantity())
                .sum();
    }

    private int calculateDiscount(int currentPrice, CouponDiscountType discountType) {
        return coupons.stream()
                .filter(coupon -> discountType.equals(coupon.getDiscountType()))
                .reduce(currentPrice, (price, coupon) -> coupon.applyDiscount(price), Integer::sum);
    }

    public int calculateDiscountedPrice() {
        int discountedPrice = calculateTotalPrice();
        discountedPrice = calculateDiscount(discountedPrice, CouponDiscountType.FIXED);
        discountedPrice = calculateDiscount(discountedPrice, CouponDiscountType.PERCENTAGE);
        return discountedPrice;
    }
}
