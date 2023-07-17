package com.github.msafriends.serviceorder.modulecore.dto;

import com.github.msafriends.serviceorder.modulecore.domain.order.OrderItem;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class OrderItemResponse {
    private ProductResponse product;

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
