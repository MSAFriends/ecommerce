package com.github.msafriends.serviceorder.modulecore.dto.response.order;

import com.github.msafriends.serviceorder.modulecore.domain.order.OrderItem;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderItemResponse {
    private final ProductResponse product;

    @Builder
    public OrderItemResponse(ProductResponse product) {
        this.product = product;
    }

    public static OrderItemResponse from(final OrderItem orderItem) {
        return OrderItemResponse.builder()
                .product(ProductResponse.from(orderItem.getProduct()))
                .build();
    }

    public static List<OrderItemResponse> from(final List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(OrderItemResponse::from)
                .toList();
    }
}
