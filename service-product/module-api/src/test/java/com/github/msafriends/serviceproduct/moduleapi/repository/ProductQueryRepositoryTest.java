package com.github.msafriends.serviceproduct.moduleapi.repository;

import java.util.List;
import java.util.Random;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.github.msafriends.serviceproduct.modulecore.domain.product.AgeLimit;
import com.github.msafriends.serviceproduct.modulecore.domain.product.Product;
import com.github.msafriends.serviceproduct.modulecore.dto.product.DiscountOrder;
import com.github.msafriends.serviceproduct.modulecore.dto.product.ProductSearchCondition;
import com.github.msafriends.serviceproduct.modulecore.dto.product.SatisfactionOrder;
import com.github.msafriends.serviceproduct.modulecore.repository.ProductQueryRepository;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductQueryRepositoryTest {
    private static final int RANDOM_INDEX = new Random().nextInt(20);

    @Autowired
    private ProductQueryRepository productQueryRepository;

    @Test
    @DisplayName("사용자는 키워드, 가격, 연령제한 조건을 통해 할인율과 만족도기준 정렬해서 상품을 조회할 수 있다.")
    void readProductsWithConditionsTest() throws Exception {
        //given
        ProductSearchCondition condition = ProductSearchCondition.of("70", 110, 400, SatisfactionOrder.DESC, DiscountOrder.DESC, AgeLimit.MINOR, null);
        //when
        List<Product> products = productQueryRepository.readProductsWithConditions(condition, 0);
        Product randomProduct = products.get(RANDOM_INDEX);
        //then
        Assertions.assertThat(products).isNotEmpty();
        Assertions.assertThat(randomProduct.getName()).contains("70");
        Assertions.assertThat(randomProduct.getPrice().getPriceValue()).isLessThan(400);
        Assertions.assertThat(randomProduct.getPrice().getPriceValue()).isGreaterThan(110);
        Assertions.assertThat(products.get(0).getBuySatisfy()).isGreaterThanOrEqualTo(products.get(18).getBuySatisfy());
        Assertions.assertThat(products.get(0).getBenefit().getDiscount()).isGreaterThanOrEqualTo(products.get(18).getBenefit().getDiscount());
    }
    @Test
    @DisplayName("사용자는 키워드 조건을 통해 상품을 조회할 수 있다.")
    void readProductsWithKeywordConditionTest() throws Exception {
        //given
        ProductSearchCondition condition = ProductSearchCondition.of("70", null, null, null, null, null, null);
        //when
        List<Product> products = productQueryRepository.readProductsWithConditions(condition, 0);
        //then
        Assertions.assertThat(products.get(RANDOM_INDEX).getName()).contains("70");
    }

    @Test
    @DisplayName("사용자는 가격 조건을 통해 상품을 조회할 수 있다.")
    void readProductsWithPriceConditionTest() throws Exception {
        //given
        ProductSearchCondition condition = ProductSearchCondition.of(null, 110, 400, null, null, null, null);
        //when
        List<Product> products = productQueryRepository.readProductsWithConditions(condition, 0);
        //then
        Assertions.assertThat(products.get(RANDOM_INDEX).getPrice().getPriceValue()).isGreaterThanOrEqualTo(110);
        Assertions.assertThat(products.get(RANDOM_INDEX).getPrice().getPriceValue()).isLessThanOrEqualTo(400);
    }

    @Test
    @DisplayName("사용자는 연령제한 조건을 통해 상품을 조회할 수 있다.")
    void readProductsWithAgeConditionTest() throws Exception {
        //given
        ProductSearchCondition condition = ProductSearchCondition.of(null, null, null, null, null, AgeLimit.MINOR, null);
        //when
        List<Product> products = productQueryRepository.readProductsWithConditions(condition, 0);
        //then
        Assertions.assertThat(products.get(RANDOM_INDEX).getAgeLimit()).isEqualTo(AgeLimit.MINOR);
    }
}
