package com.github.msafriends.serviceorder.modulecore.fixture.order;

import com.github.msafriends.serviceorder.modulecore.domain.product.Product;

public class ProductFixture {
    private static final Long DEFAULT_PRODUCT_ID = 1L;
    private static final int DEFAULT_QUANTITY = 1;
    private static final int DEFAULT_PRICE = 1000;

    private static Product.ProductBuilder createDefaultProductBuilder() {
        return Product.builder()
                .id(DEFAULT_PRODUCT_ID)
                .quantity(DEFAULT_QUANTITY)
                .price(DEFAULT_PRICE);
    }

    public static Product createDefaultProduct() {
        return createDefaultProductBuilder()
                .build();
    }

    public static Product createProductWithPrice(int price) {
        return createDefaultProductBuilder()
                .price(price)
                .build();
    }

    public static Product createProductWithQuantity(int quantity) {
        return createDefaultProductBuilder()
                .quantity(quantity)
                .build();
    }
}