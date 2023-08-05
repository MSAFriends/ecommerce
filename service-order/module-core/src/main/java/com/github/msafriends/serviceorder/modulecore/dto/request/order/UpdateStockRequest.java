package com.github.msafriends.serviceorder.modulecore.dto.request.order;

import com.github.msafriends.serviceorder.modulecore.domain.order.CartItem;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateStockRequest {
    private Long productId;

    private int quantity;

    @Builder
    public UpdateStockRequest(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public static UpdateStockRequest from(CartItem cartItem, boolean decrease) {
        return UpdateStockRequest.builder()
                .productId(cartItem.getProduct().getId())
                .quantity(cartItem.getProduct().getQuantity() * (decrease ? -1 : 1))
                .build();
    }

    public static List<UpdateStockRequest> from(List<CartItem> cartItems, boolean decrease) {
        return cartItems.stream()
                .map(cartItem -> UpdateStockRequest.from(cartItem, decrease))
                .toList();
    }
}
