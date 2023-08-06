package com.github.msafriends.serviceproduct.moduleapi.controller.internal.v1;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.msafriends.serviceproduct.moduleapi.dto.ProductReviewRequest;
import com.github.msafriends.serviceproduct.moduleapi.service.ProductReviewService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/internal/v1/product-reviews")
@RequiredArgsConstructor
public class ProductReviewController {
    private final ProductReviewService productReviewService;

    @PostMapping
    public ResponseEntity<Void> save(
        @RequestHeader("Member-Id") Long memberId,
        @Validated @RequestBody ProductReviewRequest request
    ){
        return ResponseEntity
            .created(
                URI.create("/api/internal/v1/product-reviews/"
                    + productReviewService.save(memberId, request.getProductId(), request.toProductReview())))
            .build();
    }
}
