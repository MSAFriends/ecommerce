package com.github.msafriends.serviceproduct.moduleapi.service;

import com.github.msafriends.serviceproduct.moduleapi.dto.ProductImageRequest;
import com.github.msafriends.serviceproduct.moduleapi.dto.ProductImageResponse;
import com.github.msafriends.serviceproduct.modulecore.repository.ProductImageRepository;
import com.github.msafriends.serviceproduct.modulecore.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductImageService {

    private final ProductImageRepository productImageRepository;
    private final ProductRepository productRepository;

    @Transactional
    public void registerProductImage(ProductImageRequest request, Long productId) {
        productImageRepository.save(ProductImageRequest.toEntity(request, productRepository.getProductByIdOrThrow(productId)));
    }

    public ProductImageResponse getProductImage(Long productImageId) {
        return ProductImageResponse.from(productImageRepository.findByIdOrThrow(productImageId));
    }

    public List<ProductImageResponse> getProductImageList(Long productId) {
        return productImageRepository.findAllByProductId(productId)
                .stream()
                .map(ProductImageResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteProductImage(Long productImageId) {
        productImageRepository.deleteById(productImageId);
    }
}
