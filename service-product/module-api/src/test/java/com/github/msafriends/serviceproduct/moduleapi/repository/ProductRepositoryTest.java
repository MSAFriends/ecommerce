package com.github.msafriends.serviceproduct.moduleapi.repository;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.github.msafriends.serviceproduct.modulecore.domain.product.AgeLimit;
import com.github.msafriends.serviceproduct.modulecore.domain.product.Benefit;
import com.github.msafriends.serviceproduct.modulecore.domain.product.Price;
import com.github.msafriends.serviceproduct.modulecore.domain.product.Product;
import com.github.msafriends.serviceproduct.modulecore.repository.ProductRepository;



@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

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
        List<Product> orderedProducts = productRepository.findProductsByIdIn(list);
        //when
        orderedProducts.forEach(p -> System.out.println(p.getId()));
        //then
    }
}