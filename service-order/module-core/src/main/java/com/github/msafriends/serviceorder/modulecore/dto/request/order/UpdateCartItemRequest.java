package com.github.msafriends.serviceorder.modulecore.dto.request.order;

import com.github.msafriends.serviceorder.modulecore.domain.order.CartItem;
import com.github.msafriends.serviceorder.modulecore.domain.order.Order;
import com.github.msafriends.serviceorder.modulecore.domain.product.OrderProduct;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateCartItemRequest {
    private final long productId;

    private final long sellerId;

    @NotEmpty
    private final String name;

    private final int quantity;

    @Min(0)
    private final int price;

    @Builder
    public UpdateCartItemRequest(long productId, long sellerId, String name, int quantity, int price) {
        this.productId = productId;
        this.sellerId = sellerId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public static OrderProduct toProduct(final UpdateCartItemRequest request) {
        return OrderProduct.builder()
                .id(request.getProductId())
                .name(request.getName())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .build();
    }

    public static CartItem toCartItem(final Order order, final UpdateCartItemRequest request) {
        return CartItem.builder()
                .order(order)
                .product(toProduct(request))
                .build();
    }
}
