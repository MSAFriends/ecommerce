package com.github.msafriends.serviceorder.modulecore.domain.coupon.strategy;

import com.github.msafriends.serviceorder.modulecore.domain.coupon.OrderCoupon;
import com.github.msafriends.serviceorder.modulecore.domain.order.Order;
import com.github.msafriends.serviceorder.modulecore.domain.order.CartItem;
import com.github.msafriends.serviceorder.modulecore.fixture.OrderCouponFixture;
import com.github.msafriends.serviceorder.modulecore.fixture.OrderFixture;
import com.github.msafriends.serviceorder.modulecore.fixture.CartItemFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PriceCalculatorTest {

    @Nested
    @DisplayName("전체 가격 계산 테스트")
    class TotalPriceTest {
        @Test
        @DisplayName("전체 가격을 계산한다.")
        void testCalculateTotalPrice() {
            // given
            int totalPrice = 10_000;
            int expectedTotalPrice = 10_000;
            Order order = OrderFixture.createDefaultPendingOrder();
            List<CartItem> cartItems = List.of(CartItemFixture.createCartItemWithPrice(order, totalPrice));

            // when
            PriceCalculator priceCalculator = new PriceCalculator(cartItems, Collections.emptyList());

            // then
            assertThat(priceCalculator.calculateTotalPrice()).isEqualTo(expectedTotalPrice);
        }
    }

    @Nested
    @DisplayName("할인 가격 테스트")
    class DiscountedPriceTest {
        @Test
        @DisplayName("쿠폰 할인 가격 금액이 totalPrice보다 작은 경우에 할인된 가격을 계산한다.")
        void testCalculateDiscountedPriceLessThanTotalPrice() {
            // given
            int totalPrice = 10_000;
            int expectedDiscountedPrice = 8_100;
            Order order = OrderFixture.createDefaultPendingOrder();
            List<CartItem> cartItems = List.of(CartItemFixture.createCartItemWithPrice(order, totalPrice));
            List<OrderCoupon> coupons = List.of(
                    OrderCouponFixture.createCouponWithFixedDiscount(order, 1_000),
                    OrderCouponFixture.createCouponWithPercentDiscount(order, 10));
            order.addCoupons(coupons);

            // when
            PriceCalculator priceCalculator = new PriceCalculator(cartItems, coupons);

            // then
            assertThat(priceCalculator.calculateDiscountedPrice()).isEqualTo(expectedDiscountedPrice);
        }

        @Test
        @DisplayName("쿠폰 할인 가격 금액이 totalPrice보다 큰 경우에 0을 반환한다.")
        void testCalculateDiscountedPriceGreaterThanTotalPrice() {
            // given
            int totalPrice = 10_000;
            int expectedTotalPrice = 0;
            Order order = OrderFixture.createDefaultPendingOrder();
            List<CartItem> cartItems = List.of(CartItemFixture.createCartItemWithPrice(order, totalPrice));
            List<OrderCoupon> coupons = List.of(OrderCouponFixture.createCouponWithFixedDiscount(order, 12_000));
            order.addCoupons(coupons);

            // when
            PriceCalculator priceCalculator = new PriceCalculator(cartItems, coupons);

            // then
            assertThat(priceCalculator.calculateDiscountedPrice()).isEqualTo(expectedTotalPrice);
        }
    }
}