package com.github.msafriends.serviceproduct.moduleapi.controller.external.v1;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.msafriends.serviceproduct.modulecore.domain.product.AgeLimit;
import com.github.msafriends.serviceproduct.modulecore.domain.product.Product;
import com.github.msafriends.serviceproduct.modulecore.dto.product.DiscountOrder;
import com.github.msafriends.serviceproduct.modulecore.dto.product.ProductResponse;
import com.github.msafriends.serviceproduct.modulecore.dto.product.ProductSearchCondition;
import com.github.msafriends.serviceproduct.modulecore.dto.product.SatisfactionOrder;
import com.github.msafriends.serviceproduct.modulecore.repository.ProductQueryRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/external/v1/products")
public class ProductExternalApiControllerV1 {
    private final ProductQueryRepository productQueryRepository;

    @GetMapping
    public Page<ProductResponse> findProductsWithConditions(
        @RequestParam(required = false, defaultValue = "1") int page,
        @RequestParam(required = false) String keyword,
        @RequestParam(required = false) Integer minPrice,
        @RequestParam(required = false) Integer maxPrice,
        @RequestParam(required = false) AgeLimit ageLimit,
        @RequestParam(required = false) Long categoryId,
        @RequestParam(required = false) SatisfactionOrder satisfactionOrder,
        @RequestParam(required = false) DiscountOrder discountOrder
    ){
        ProductSearchCondition condition = ProductSearchCondition
            .of(keyword, minPrice, maxPrice, satisfactionOrder, discountOrder, ageLimit, categoryId);
        List<Product> products = productQueryRepository.readProductsWithConditions(condition, page - 1);
        return PageableExecutionUtils
            .getPage(products.stream().map(ProductResponse::from).toList(), PageRequest.of(0, products.size()), products::size);
    }
}
