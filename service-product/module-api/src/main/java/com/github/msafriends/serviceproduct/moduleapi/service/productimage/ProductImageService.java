package com.github.msafriends.serviceproduct.moduleapi.service.productimage;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.msafriends.serviceproduct.modulecore.dto.productimage.ProductImageResponse;
import com.github.msafriends.serviceproduct.modulecore.repository.ProductImageRepository;
import com.github.msafriends.serviceproduct.modulecore.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductImageService {

    private final ProductImageRepository productImageRepository;
    private final ProductRepository productRepository;

    public ProductImageResponse getProductImage(Long productImageId) {
        return ProductImageResponse.from(productImageRepository.findByIdOrThrow(productImageId));
    }

    public List<ProductImageResponse> getProductImages(Long productId) {
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
