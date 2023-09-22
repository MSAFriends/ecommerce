package com.github.msafriends.serviceproduct.moduleapi.controller.external.v1;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.msafriends.serviceproduct.moduleapi.service.productimage.ProductImageService;
import com.github.msafriends.serviceproduct.modulecore.dto.productimage.ProductImageResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products/{productId}/images")
public class ProductImageExternalApiControllerV1 {

    private final ProductImageService productImageService;

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
