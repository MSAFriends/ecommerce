package com.github.msafriends.serviceproduct.moduleapi.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.msafriends.serviceproduct.moduleapi.dto.UpdateStockRequest;
import com.github.msafriends.serviceproduct.modulecore.domain.category.Category;
import com.github.msafriends.serviceproduct.modulecore.repository.CategoryRepository;
import com.github.msafriends.serviceproduct.modulecore.repository.ProductRepository;

import com.github.msafriends.serviceproduct.modulecore.domain.product.Product;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {
	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;

	@Transactional
	public Long registerProduct(Long categoryId, Product product){
		if(categoryId != null){
			Category category = categoryRepository.findByIdOrThrow(categoryId);
			product.assignCategory(category);
		}
		return productRepository.save(product).getId();
	}

	@Transactional
	public Long registerProduct(Product product){
		return productRepository.save(product).getId();
	}

	@Transactional
	public void updateStocks(List<UpdateStockRequest> updateStockRequests){
		List<Long> orderedIds = updateStockRequests.stream()
            .map(UpdateStockRequest::getProductId)
			.toList();
		Map<Long, Integer> requestMap = updateStockRequests.stream()
			.collect(Collectors.toMap(UpdateStockRequest::getProductId, UpdateStockRequest::getQuantity));
		List<Product> foundProducts = productRepository.findProductsByIdIn(orderedIds);
		foundProducts.forEach(product -> product.updateStockQuantity(requestMap.get(product.getId())));
	}
}
