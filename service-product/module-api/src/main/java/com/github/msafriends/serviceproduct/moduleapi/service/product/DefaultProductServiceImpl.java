package com.github.msafriends.serviceproduct.moduleapi.service.product;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.msafriends.modulecommon.exception.EntityNotFoundException;
import com.github.msafriends.modulecommon.exception.ErrorCode;
import com.github.msafriends.serviceproduct.moduleapi.dto.UpdateStockRequest;
import com.github.msafriends.serviceproduct.modulecore.domain.category.Category;
import com.github.msafriends.serviceproduct.modulecore.domain.product.Product;
import com.github.msafriends.serviceproduct.modulecore.repository.CategoryRepository;
import com.github.msafriends.serviceproduct.modulecore.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@Qualifier("defaultProductService")
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefaultProductServiceImpl implements ProductService{
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
		if(orderedIds.size() != foundProducts.size())
			throw new EntityNotFoundException(ErrorCode.INVALID_ORDER_ERROR, "유효하지 않은 상품 id가 주문에 포함되어 있습니다.");
		foundProducts.forEach(product -> product.updateStockQuantity(requestMap.get(product.getId())));
	}

	@Transactional
	public void updateEachStock(final UpdateStockRequest updateStockRequest){
		Product product = productRepository.findByIdOrThrow(updateStockRequest.getProductId());
		product.updateStockQuantity(updateStockRequest.getQuantity());
	}

	public List<Product>readProductsBySellerId(Long sellerId){
		return productRepository.findTop1000ProductsBySellerId(sellerId);
	}

	@Override
	public List<Product> readProductsByCategoryId(Long categoryId) {
		return productRepository.findTop1000ProductByCategoryId(categoryId);
	}
}
