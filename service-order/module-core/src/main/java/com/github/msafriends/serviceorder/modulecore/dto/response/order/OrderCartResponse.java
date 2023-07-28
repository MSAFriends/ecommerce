package com.github.msafriends.serviceorder.modulecore.dto.response.order;

import com.github.msafriends.serviceorder.modulecore.domain.order.Order;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderCartResponse {
    private final Long id;
    private final Long memberId;
    private final PriceResponse price;
    private final List<CartItemResponse> cartItems;

    @Builder
    public OrderCartResponse(Long id, Long memberId, PriceResponse price, List<CartItemResponse> cartItems) {
        this.id = id;
        this.memberId = memberId;
        this.price = price;
        this.cartItems = cartItems;
    }

    public static OrderCartResponse from(final Order order) {
        return OrderCartResponse.builder()
                .id(order.getId())
                .memberId(order.getMemberId())
                .price(PriceResponse.from(order))
                .cartItems(CartItemResponse.from(order.getCartItems()))
                .build();
    }
}