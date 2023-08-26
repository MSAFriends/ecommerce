package com.github.msafriends.serviceproduct.moduleapi.controller.internal.v1;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.msafriends.serviceproduct.moduleapi.service.product.ProductService;
import com.github.msafriends.serviceproduct.modulecore.aop.annotation.ExeTimer;
import com.github.msafriends.serviceproduct.modulecore.dto.product.ProductRequest;
import com.github.msafriends.serviceproduct.modulecore.dto.product.UpdateStockRequest;

@RestController
@RequestMapping("/api/internal/v1/products")
public class ProductInternalApiControllerV1 {

	private final ProductService productService;


	public ProductInternalApiControllerV1(
		ProductService productService
	) {
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

	@ExeTimer
	@PostMapping("/stocks")
	public ResponseEntity<Void> bulkUpdateProductStocks(@RequestBody List<UpdateStockRequest> requests){
		productService.updateStocks(requests);
		return ResponseEntity.ok().build();
	}
}