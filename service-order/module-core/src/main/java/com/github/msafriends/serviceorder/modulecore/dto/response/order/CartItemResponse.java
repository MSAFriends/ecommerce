package com.github.msafriends.serviceorder.modulecore.dto.response.order;

import com.github.msafriends.serviceorder.modulecore.domain.order.CartItem;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CartItemResponse {
    private final ProductResponse product;

    @Builder
    public CartItemResponse(ProductResponse product) {
        this.product = product;
    }

    public static CartItemResponse from(final CartItem cartItem) {
        return CartItemResponse.builder()
                .product(ProductResponse.from(cartItem.getProduct()))
                .build();
    }

    public static List<CartItemResponse> from(final List<CartItem> cartItems) {
        return cartItems.stream()
                .map(CartItemResponse::from)
                .toList();
    }
}
