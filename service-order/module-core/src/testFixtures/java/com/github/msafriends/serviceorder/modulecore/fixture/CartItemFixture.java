package com.github.msafriends.serviceorder.modulecore.fixture;

import com.github.msafriends.serviceorder.modulecore.domain.order.Order;
import com.github.msafriends.serviceorder.modulecore.domain.order.CartItem;
import com.github.msafriends.serviceorder.modulecore.domain.product.Product;
import com.github.msafriends.serviceorder.modulecore.dto.request.order.CartItemRequest;

public class CartItemFixture {
    private static final Product DEFAULT_PRODUCT = ProductFixture.createDefaultProduct();

    private static CartItem.CartItemBuilder createDefaultCartItemBuilder(Order order) {
        return CartItem.builder()
                .order(order)
                .product(DEFAULT_PRODUCT);
    }

    public static CartItem createDefaultCartItem(Order order) {
        return createDefaultCartItemBuilder(order)
                .build();
    }

    public static CartItem createCartItemWithPrice(Order order, int price) {
        return createDefaultCartItemBuilder(order)
                .product(ProductFixture.createProductWithPrice(price))
                .build();
    }

    public static CartItem createCartItemWithQuantity(Order order, int quantity) {
        return createDefaultCartItemBuilder(order)
                .product(ProductFixture.createProductWithQuantity(quantity))
                .build();
    }

    private static CartItemRequest.CartItemRequestBuilder createDefaultCartItemRequest() {
        return CartItemRequest.builder()
                .productId(ProductFixture.DEFAULT_PRODUCT_ID)
                .quantity(ProductFixture.DEFAULT_QUANTITY);
    }

    public static CartItemRequest createCartItemRequestWithQuantity(int quantity) {
        return createDefaultCartItemRequest()
                .quantity(quantity)
                .build();
    }

    public static CartItemRequest createCartItemRequestWithProductId(Long productId) {
        return createDefaultCartItemRequest()
                .productId(productId)
                .build();
    }
}
