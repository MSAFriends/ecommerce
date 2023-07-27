package com.github.msafriends.serviceorder.modulecore.fixture;

import com.github.msafriends.serviceorder.modulecore.domain.product.Product;
import com.github.msafriends.serviceorder.modulecore.dto.response.order.ProductResponse;

public class ProductFixture {
    public static final String DEFAULT_PRODUCT_NAME = "테스트 상품";
    public static final Long DEFAULT_SELLER_ID = 1L;
    public static final Long DEFAULT_PRODUCT_ID = 1L;
    public static final int DEFAULT_QUANTITY = 1;
    public static final int DEFAULT_PRICE = 1000;

    private static Product.ProductBuilder createDefaultProductBuilder() {
        return Product.builder()
                .id(DEFAULT_PRODUCT_ID)
                .name(DEFAULT_PRODUCT_NAME)
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

    private static ProductResponse.ProductResponseBuilder createDefaultProductResponse() {
        return ProductResponse.builder()
                .id(DEFAULT_PRODUCT_ID)
                .quantity(DEFAULT_QUANTITY)
                .price(DEFAULT_PRICE);
    }

    public static ProductResponse createDefaultProductResponseWithPrice(int price) {
        return createDefaultProductResponse()
                .price(price)
                .build();
    }

    public static ProductResponse createDefaultProductResponseWithQuantity(int quantity) {
        return createDefaultProductResponse()
                .quantity(quantity)
                .build();
    }

    public static ProductResponse createDefaultProductResponseWithId(Long id) {
        return createDefaultProductResponse()
                .id(id)
                .build();
    }
}