package com.github.msafriends.serviceproduct.common.fixture.product;

import org.springframework.test.util.ReflectionTestUtils;

import com.github.msafriends.serviceproduct.moduleapi.dto.ProductRequest;

import com.github.msafriends.serviceproduct.common.fixture.category.CategoryFixture;
import com.github.msafriends.serviceproduct.modulecore.domain.product.AgeLimit;
import com.github.msafriends.serviceproduct.modulecore.domain.product.Benefit;
import com.github.msafriends.serviceproduct.modulecore.domain.product.Price;
import com.github.msafriends.serviceproduct.modulecore.domain.product.Product;
import com.github.msafriends.serviceproduct.modulecore.domain.product.Size;

public class ProductFixture {

    public static final Long TEST_PRODUCT_ID = 1L;
    public static final Long SELLER_ID = 1L;
    public static final Long CODE = 111111111L;
    public static final int PRICE_VALUE = 1000;
    public static final int NEGATIVE_PRICE_VALUE = -1;
    public static final int ADDITIONAL_PRICE_AMOUNT = 1000;
    public static final int SALE_PRICE_VALUE = 900;
    public static final Price DEFAULT_PRICE = createDefaultPrice();
    public static final Integer QUANTITY = 10;
    public static final String NAME = "상품명";
    public static final float RATING = 2.5F;
    public static final String DETAIL_PAGE_URL = "localhost:10000/asset/image.jpg";
    public static final String DELIVERY = "착불/선결제";
    public static final int REVIEW_COUNT = 10;
    public static final int BUY_SATISFY = 90;
    public static final AgeLimit AGE_LIMIT = AgeLimit.ADULT;
    public static final int DISCOUNT_RATE = 40;
    public static final int MIN_DISCOUNT_RATE = 0;
    public static final int MAX_DISCOUNT_RATE = 100;
    public static final int MILEAGE = 0;
    public static final Size DEFAULT_SIZE = Size.MEDIUM;
    public static final Benefit DEFAULT_BENEFIT = createDefaultBenefit();

    public static Price createDefaultPrice(){
        return Price.builder()
            .priceValue(PRICE_VALUE)
            .salePriceValue(SALE_PRICE_VALUE)
            .build();
    }

    public static Price createInvalidPrice(int price, int salesPrice){
        return Price.builder()
            .priceValue(price)
            .salePriceValue(salesPrice)
            .build();
    }

    public static Benefit createDefaultBenefit() {
        return Benefit.builder()
            .discount(DISCOUNT_RATE)
            .mileage(MILEAGE)
            .build();
    }
    public static Product createProduct() {
        return Product.builder()
                .sellerId(SELLER_ID)
                .code(CODE)
                .price(DEFAULT_PRICE)
                .name(NAME)
                .delivery(DELIVERY)
                .buySatisfy(BUY_SATISFY)
                .ageLimit(AGE_LIMIT)
                .size(DEFAULT_SIZE)
                .benefit(createDefaultBenefit())
                .build();
    }

    public static ProductRequest createProductRequest(){
        return ProductRequest.builder()
            .code(CODE)
            .price(createDefaultPrice())
            .quantity(QUANTITY)
            .name(NAME)
            .categoryId(CategoryFixture.MAIN_CATEGORY_ID_A)
            .delivery(DELIVERY)
            .ageLimit(AGE_LIMIT)
            .benefit(createDefaultBenefit())
            .buySatisfy(BUY_SATISFY)
            .build();
    }

    public static Product createProductWithId(Long id){
        Product product = createProduct();
        ReflectionTestUtils.setField(product, "id", id);
        return product;
    }


    public static Product createInvalidPriceProduct(int price, int salePrice) {
        return Product.builder()
                .sellerId(SELLER_ID)
                .code(CODE)
                .price(createInvalidPrice(price, salePrice))
                .name(NAME)
                .delivery(DELIVERY)
                .buySatisfy(BUY_SATISFY)
                .ageLimit(AGE_LIMIT)
                .benefit(createDefaultBenefit())
                .size(DEFAULT_SIZE)
                .build();
    }
}
