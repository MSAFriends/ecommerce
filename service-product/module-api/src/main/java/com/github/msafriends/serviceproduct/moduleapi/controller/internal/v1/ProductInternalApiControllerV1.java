package com.github.msafriends.serviceproduct.moduleapi.controller.internal.v1;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.msafriends.serviceproduct.moduleapi.dto.ProductRequest;
import com.github.msafriends.serviceproduct.moduleapi.dto.ProductResponse;
import com.github.msafriends.serviceproduct.moduleapi.dto.UpdateStockRequest;
import com.github.msafriends.serviceproduct.moduleapi.service.ProductService;
import com.github.msafriends.serviceproduct.moduleapi.service.facade.LettuceLockProductFacade;

@RestController
@RequestMapping("/api/internal/v1/products")
public class ProductInternalApiControllerV1 {
	private final LettuceLockProductFacade productFacade;
	private final ProductService productService;

	public ProductInternalApiControllerV1(LettuceLockProductFacade productFacade, @Qualifier("defaultProductService") ProductService productService) {
		this.productFacade = productFacade;
		this.productService = productService;
	}

	@PostMapping
	public ResponseEntity<Void> save(
		@RequestHeader("Seller-Id") Long sellerId,
		@Validated @RequestBody ProductRequest request
	){
		return ResponseEntity
			.created(
				URI.create("/api/internal/v1/products/"
					+ productService.registerProduct(request.toEntity(sellerId)))
			)
			.build();
	}

	@PostMapping("/stocks")
	public ResponseEntity<Void> bulkUpdateProductStocks(@RequestBody List<UpdateStockRequest> requests){
		productFacade.updateStocks(requests);
		return ResponseEntity.ok().build();
	}

	@GetMapping
	public ResponseEntity<List<ProductResponse>> getProductsBySellerId(
		@RequestHeader("Seller-Id") Long sellerId
	){
		List<ProductResponse> responses = productService.readProductsBySellerId(sellerId)
			.stream()
			.map(ProductResponse::from)
			.toList();
		return ResponseEntity.ok(responses);
	}
}