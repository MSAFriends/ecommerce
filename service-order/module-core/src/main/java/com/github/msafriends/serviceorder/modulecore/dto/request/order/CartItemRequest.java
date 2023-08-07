package com.github.msafriends.serviceorder.modulecore.dto.request.order;

import com.github.msafriends.serviceorder.modulecore.domain.order.CartItem;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CartItemRequest {
    private final Long productId;

    @Min(1)
    private final Integer quantity;

    @Builder
    public CartItemRequest(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public static CartItemRequest from(CartItem cartItem) {
        return CartItemRequest.builder()
                .productId(cartItem.getProduct().getId())
                .quantity(cartItem.getProduct().getQuantity())
                .build();
    }

    public static List<CartItemRequest> from(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(CartItemRequest::from)
                .toList();
    }
}
