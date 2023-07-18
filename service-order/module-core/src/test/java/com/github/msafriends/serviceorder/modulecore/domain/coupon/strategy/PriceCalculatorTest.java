package com.github.msafriends.serviceorder.modulecore.domain.coupon.strategy;

import com.github.msafriends.serviceorder.modulecore.domain.order.Order;
import com.github.msafriends.serviceorder.modulecore.domain.order.OrderItem;
import com.github.msafriends.serviceorder.modulecore.dto.CouponResponse;
import com.github.msafriends.serviceorder.modulecore.fixture.order.CouponFixture;
import com.github.msafriends.serviceorder.modulecore.fixture.order.OrderFixture;
import com.github.msafriends.serviceorder.modulecore.fixture.order.OrderItemFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PriceCalculatorTest {

    @Test
    @DisplayName("전체 가격 계산 테스트")
    void testCalculateTotalPrice() {
        // given
        int totalPrice = 10_000;
        int expectedTotalPrice = 10_000;
        Order order = OrderFixture.createDefaultOrder();
        List<OrderItem> orderItems = List.of(OrderItemFixture.createOrderItemWithPrice(order, totalPrice));

        // when
        PriceCalculator priceCalculator = new PriceCalculator(orderItems, Collections.emptyList());

        // then
        assertThat(priceCalculator.calculateTotalPrice()).isEqualTo(expectedTotalPrice);
    }

    @Test
    @DisplayName("쿠폰 할인 가격 계산 테스트")
    void testCalculateDiscountedPrice() {
        // given
        int totalPrice = 10_000;
        int expectedDiscountedPrice = 8_100;
        Order order = OrderFixture.createDefaultOrder();
        List<OrderItem> orderItems = List.of(OrderItemFixture.createOrderItemWithPrice(order, totalPrice));
        List<CouponResponse> coupons = List.of(
                CouponFixture.createCouponWithFixedDiscount(1_000),
                CouponFixture.createCouponWithPercentDiscount(10));

        // when
        PriceCalculator priceCalculator = new PriceCalculator(orderItems, coupons);

        // then
        assertThat(priceCalculator.calculateDiscountedPrice()).isEqualTo(expectedDiscountedPrice);
    }
}