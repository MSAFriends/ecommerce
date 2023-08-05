package com.github.msafriends.serviceproduct.moduleapi.controller.external.v1;

import com.github.msafriends.serviceproduct.moduleapi.dto.ProductImageRequest;
import com.github.msafriends.serviceproduct.moduleapi.dto.ProductImageResponse;
import com.github.msafriends.serviceproduct.moduleapi.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products/{productId}/image")
public class ProductImageExternalApiControllerV1 {

    private final ProductImageService productImageService;

    @PostMapping
    public ResponseEntity<Void> registerProductImage(@PathVariable Long productId, @RequestBody ProductImageRequest request) {
        productImageService.registerProductImage(request, productId);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{productImageId}")
    public ResponseEntity<ProductImageResponse> getProductImage(@PathVariable Long productId, @PathVariable Long productImageId) {
        return ResponseEntity.ok(productImageService.getProductImage(productImageId));
    }

    @GetMapping
    public ResponseEntity<List<ProductImageResponse>> getProductImageList(@PathVariable Long productId) {
        return ResponseEntity.ok(productImageService.getProductImageList(productId));
    }

    @DeleteMapping("/{productImageId}")
    public ResponseEntity<Void> deleteProductImage(@PathVariable Long productId, @PathVariable Long productImageId) {
        productImageService.deleteProductImage(productImageId);
        return ResponseEntity.ok().build();
    }

}
