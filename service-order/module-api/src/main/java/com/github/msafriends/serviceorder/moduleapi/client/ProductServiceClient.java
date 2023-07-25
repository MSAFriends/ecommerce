package com.github.msafriends.serviceorder.moduleapi.client;

import com.github.msafriends.serviceorder.modulecore.dto.response.order.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service")
public interface ProductServiceClient {
    @GetMapping("/api/v1/products/{productId}")
    ProductResponse getProduct(@PathVariable Long productId);
}
