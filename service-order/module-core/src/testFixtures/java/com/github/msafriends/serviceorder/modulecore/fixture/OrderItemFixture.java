package com.github.msafriends.serviceorder.modulecore.fixture;

import com.github.msafriends.serviceorder.modulecore.domain.order.Order;
import com.github.msafriends.serviceorder.modulecore.domain.order.OrderItem;
import com.github.msafriends.serviceorder.modulecore.domain.product.Product;
import com.github.msafriends.serviceorder.modulecore.dto.request.order.OrderItemRequest;

public class OrderItemFixture {
    private static final Product DEFAULT_PRODUCT = ProductFixture.createDefaultProduct();

    private static OrderItem.OrderItemBuilder createDefaultOrderItemBuilder(Order order) {
        return OrderItem.builder()
                .order(order)
                .product(DEFAULT_PRODUCT);
    }

    public static OrderItem createDefaultOrderItem(Order order) {
        return createDefaultOrderItemBuilder(order)
                .build();
    }

    public static OrderItem createOrderItemWithPrice(Order order, int price) {
        return createDefaultOrderItemBuilder(order)
                .product(ProductFixture.createProductWithPrice(price))
                .build();
    }

    public static OrderItem createOrderItemWithQuantity(Order order, int quantity) {
        return createDefaultOrderItemBuilder(order)
                .product(ProductFixture.createProductWithQuantity(quantity))
                .build();
    }

    private static OrderItemRequest.OrderItemRequestBuilder createDefaultOrderItemRequest() {
        return OrderItemRequest.builder()
                .productId(ProductFixture.DEFAULT_PRODUCT_ID)
                .quantity(ProductFixture.DEFAULT_QUANTITY);
    }

    public static OrderItemRequest createOrderItemRequestWithQuantity(int quantity) {
        return createDefaultOrderItemRequest()
                .quantity(quantity)
                .build();
    }

    public static OrderItemRequest createOrderItemRequestWithProductId(Long productId) {
        return createDefaultOrderItemRequest()
                .productId(productId)
                .build();
    }
}
