package com.github.msafriends.serviceorder.modulecore.fixture;

import com.github.msafriends.serviceorder.modulecore.domain.order.Order;
import com.github.msafriends.serviceorder.modulecore.domain.order.CartItem;
import com.github.msafriends.serviceorder.modulecore.domain.product.OrderProduct;
import com.github.msafriends.serviceorder.modulecore.dto.request.order.CartItemRequest;
import com.github.msafriends.serviceorder.modulecore.dto.request.order.UpdateCartItemRequest;

public class CartItemFixture {
    private static final OrderProduct DEFAULT_PRODUCT = ProductFixture.createDefaultProduct();

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

    private static UpdateCartItemRequest.UpdateCartItemRequestBuilder createDefaultUpdateCartItemRequest() {
        return UpdateCartItemRequest.builder()
                .name(ProductFixture.DEFAULT_PRODUCT_NAME)
                .sellerId(ProductFixture.DEFAULT_SELLER_ID)
                .price(ProductFixture.DEFAULT_PRICE)
                .productId(ProductFixture.DEFAULT_PRODUCT_ID)
                .quantity(ProductFixture.DEFAULT_QUANTITY);
    }

    public static UpdateCartItemRequest createUpdateCartItemRequestWithProductId(Long productId) {
        return createDefaultUpdateCartItemRequest()
                .productId(productId)
                .build();
    }

    public static UpdateCartItemRequest createUpdateCartItemRequestWithSellerId(Long sellerId) {
        return createDefaultUpdateCartItemRequest()
                .sellerId(sellerId)
                .build();
    }

    public static UpdateCartItemRequest createUpdateCartItemRequestWithQuantity(int quantity) {
        return createDefaultUpdateCartItemRequest()
                .quantity(quantity)
                .build();
    }

    public static UpdateCartItemRequest createUpdateCartItemRequestWithPrice(int price) {
        return createDefaultUpdateCartItemRequest()
                .price(price)
                .build();
    }

    public static UpdateCartItemRequest createUpdateCartItemRequestWithName(String name) {
        return createDefaultUpdateCartItemRequest()
                .name(name)
                .build();
    }
}
