package com.github.msafriends.serviceproduct.moduleapi.controller.external.v1;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.msafriends.serviceproduct.moduleapi.service.product.ProductService;
import com.github.msafriends.serviceproduct.modulecore.domain.product.AgeLimit;
import com.github.msafriends.serviceproduct.modulecore.domain.product.Product;
import com.github.msafriends.serviceproduct.modulecore.dto.product.DiscountOrder;
import com.github.msafriends.serviceproduct.modulecore.dto.product.PopularProducts;
import com.github.msafriends.serviceproduct.modulecore.dto.product.PopularProductsCategory;
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
    private final ProductService productService;

    /**
     * @deprecated deprecated since the end point added for tuned query
     * @param page
     * @param keyword
     * @param minPrice
     * @param maxPrice
     * @param ageLimit
     * @param categoryId
     * @param satisfactionOrder
     * @param discountOrder
     * @return
     */
    @Deprecated(since = "query tuning for performance")
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

    @GetMapping
    public Page<ProductResponse> queryTunedWithConditions(
        @RequestParam(required = false, defaultValue = "1") int page,
        @RequestParam(required = false) String keyword,
        @RequestParam(required = false) Integer minPrice,
        @RequestParam(required = false) Integer maxPrice,
        @RequestParam(required = false) AgeLimit ageLimit,
        @RequestParam(required = false) Long categoryId,
        @RequestParam(required = false) SatisfactionOrder satisfactionOrder
    ){
        ProductSearchCondition condition = ProductSearchCondition
            .of(keyword, minPrice, maxPrice, satisfactionOrder, ageLimit, categoryId);
        List<Product> products = productQueryRepository.tunedQueryWithConditions(condition, page - 1);
        return PageableExecutionUtils
            .getPage(products.stream().map(ProductResponse::from).toList(), PageRequest.of(0, products.size()), products::size);
    }

    @GetMapping("/sellers/{sellerId}")
    public ResponseEntity<Page<ProductResponse>> getProductsBySellerId(
        @PathVariable Long sellerId,
        @RequestParam(defaultValue = "1") int page
    ){
        Page<ProductResponse> responses = productService.readProductsBySellerId(sellerId, PageRequest.of(page - 1, 20))
            .map(ProductResponse::from);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<Page<ProductResponse>> getProductsByCategoryId(
        @PathVariable Long categoryId,
        @RequestParam(defaultValue = "1") int page
    ){
        Page<ProductResponse> responses = productService.readProductsByCategoryId(categoryId, PageRequest.of(page - 1, 20))
            .map(ProductResponse::from);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/popular")
    public ResponseEntity<PopularProducts> getTop10PopularProducts(){
        return ResponseEntity.ok(productService.readTop10PopularProducts());
    }

    @GetMapping("/categories/{categoryId}/popular")
    public ResponseEntity<PopularProductsCategory> getTop10PopularProductForCategory(
        @PathVariable Long categoryId
    ){
        return ResponseEntity.ok(productService.readTop10PopularProductsForCategory(categoryId));
    }
}
