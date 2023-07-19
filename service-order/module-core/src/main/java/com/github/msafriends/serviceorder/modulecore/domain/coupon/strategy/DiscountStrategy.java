package com.github.msafriends.serviceorder.modulecore.domain.coupon.strategy;

public interface DiscountStrategy {
    int apply(int originalPrice, int discountValue);
}
