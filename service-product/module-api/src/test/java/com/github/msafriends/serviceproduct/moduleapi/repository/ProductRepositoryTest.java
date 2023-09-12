package com.github.msafriends.serviceproduct.moduleapi.repository;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import com.github.msafriends.serviceproduct.common.fixture.category.CategoryFixture;
import com.github.msafriends.serviceproduct.common.fixture.product.ProductFixture;
import com.github.msafriends.serviceproduct.modulecore.domain.product.AgeLimit;
import com.github.msafriends.serviceproduct.modulecore.domain.product.Benefit;
import com.github.msafriends.serviceproduct.modulecore.domain.product.Price;
import com.github.msafriends.serviceproduct.modulecore.domain.product.Product;
import com.github.msafriends.serviceproduct.modulecore.repository.CategoryRepository;
import com.github.msafriends.serviceproduct.modulecore.repository.ProductRepository;

import io.lettuce.core.ScriptOutputType;

@DataJpaTest
@Transactional
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @BeforeEach
    void setup(){
        productRepository.save(
            Product.builder()
                .ageLimit(AgeLimit.ADULT)
                .code(1234L)
                .price(Price.builder()
                    .priceValue(111).build())
                .sellerId(1L)
                .delivery("seoul")
                .benefit(Benefit.builder()
                    .discount(10)
                    .mileage(1)
                    .build())
                .name("product1").build());
        productRepository.save(
            Product.builder()
                .ageLimit(AgeLimit.ADULT)
                .code(123L)
                .price(Price.builder()
                    .priceValue(111).build())
                .sellerId(1L)
                .delivery("seoul")
                .benefit(Benefit.builder()
                    .discount(10)
                    .mileage(1)
                    .build())
                .name("product2").build());
        productRepository.save(
            Product.builder()
                .ageLimit(AgeLimit.ADULT)
                .code(124L)
                .price(Price.builder()
                    .priceValue(111).build())
                .sellerId(1L)
                .delivery("seoul")
                .benefit(Benefit.builder()
                    .discount(10)
                    .mileage(1)
                    .build())
                .name("product3").build());
        productRepository.save(
            Product.builder()
                .ageLimit(AgeLimit.ADULT)
                .code(14L)
                .price(Price.builder()
                    .priceValue(111).build())
                .sellerId(1L)
                .delivery("seoul")
                .benefit(Benefit.builder()
                    .discount(10)
                    .mileage(1)
                    .build())
                .name("product4").build());
    }


    @Test
    @DisplayName("PessimisticLock을 활용한 Product 다량 조회")
    void findProductsByIdWithPessimisticLocTest() throws Exception {
        //given
        List<Long> list = Arrays.asList(1L, 2L, 3L, 4L);
        //when
        List<Product> orderedProducts = productRepository.findProductsByIdIn(list);
        //then
        Assertions.assertThat(orderedProducts).hasSize(4);
    }


    @Test
    @DisplayName("카테고리 id로 인기 상품 10개 조회 성공")
    void readTop10PopularProductsByCategoryIdOrderByBuySatisfyTest() throws Exception {
        //given
        Long categoryId = 1L;
        categoryRepository.save(CategoryFixture.createMainCategoryWithId(categoryId, "가전제품"));
        productRepository.save(ProductFixture.createProductWithCategoryId(categoryId));
        //when
        List<Product> popularProductsForCategory = productRepository.findTop10ByCategoryIdOrderByBuySatisfy(
            categoryId);
        System.out.println(popularProductsForCategory.size());
        //then
        Assertions.assertThat(popularProductsForCategory).isNotEmpty();
    }
}