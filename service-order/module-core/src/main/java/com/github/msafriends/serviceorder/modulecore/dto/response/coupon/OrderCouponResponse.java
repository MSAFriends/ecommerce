package com.github.msafriends.serviceorder.modulecore.dto.response.coupon;

import com.github.msafriends.serviceorder.modulecore.domain.coupon.OrderCoupon;
import com.github.msafriends.serviceorder.modulecore.domain.coupon.CouponDiscountType;
import com.github.msafriends.serviceorder.modulecore.domain.order.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderCouponResponse {
    private Long id;
    private String name;
    private CouponDiscountType discountType;
    private int value;

    @Builder
    public OrderCouponResponse(Long id, String name, CouponDiscountType discountType, int value) {
        this.id = id;
        this.name = name;
        this.discountType = discountType;
        this.value = value;
    }

    public static OrderCoupon toCoupon(final Order order, final OrderCouponResponse couponResponse) {
        return OrderCoupon.builder()
                .couponId(couponResponse.getId())
                .order(order)
                .name(couponResponse.getName())
                .discountValue(couponResponse.getValue())
                .discountType(couponResponse.getDiscountType())
                .build();
    }
}
