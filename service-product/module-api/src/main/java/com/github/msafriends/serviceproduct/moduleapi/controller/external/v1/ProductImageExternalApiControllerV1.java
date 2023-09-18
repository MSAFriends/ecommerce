package com.github.msafriends.serviceproduct.moduleapi.controller.external.v1;

import com.github.msafriends.serviceproduct.modulecore.dto.productimage.ProductImageRequest;
import com.github.msafriends.serviceproduct.modulecore.dto.productimage.ProductImageResponse;
import com.github.msafriends.serviceproduct.moduleapi.service.productimage.ProductImageService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products/{productId}/images")
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
        return ResponseEntity.ok(productImageService.getProductImages(productId));
    }

    @DeleteMapping("/{productImageId}")
    public ResponseEntity<Void> deleteProductImage(@PathVariable Long productId, @PathVariable Long productImageId) {
        productImageService.deleteProductImage(productImageId);
        return ResponseEntity.ok().build();
    }

}
