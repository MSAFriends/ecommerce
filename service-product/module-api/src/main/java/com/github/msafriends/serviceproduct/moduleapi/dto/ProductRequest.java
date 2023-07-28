package com.github.msafriends.serviceproduct.moduleapi.dto;

import domain.product.AgeLimit;
import domain.product.Benefit;
import domain.product.Price;
import domain.product.Product;
import domain.product.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductRequest {
	private Long sellerId;
	@NotNull
	private Long code;
	@NotBlank
	private String name;
	@NotNull
	private Price price;
	@Min(value = 0)
	@NotNull
	private Integer quantity;
	@NotNull
	private String delivery;
	private Integer buySatisfy;
	private Benefit benefit;
	@NotNull
	private AgeLimit ageLimit;
	private Size size;
	private Long categoryId;

	@Builder
	public ProductRequest(Long code, String name, Price price, Integer quantity, String delivery, Integer buySatisfy,
		Benefit benefit, AgeLimit ageLimit, Size size, Long categoryId) {
		this.code = code;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.delivery = delivery;
		this.buySatisfy = buySatisfy;
		this.benefit = benefit;
		this.ageLimit = ageLimit;
		this.size = size;
		this.categoryId = categoryId;
	}

	public Product toEntity(Long sellerId){
		return Product.builder()
			.sellerId(sellerId)
			.name(name)
			.code(code)
			.price(price)
			.quantity(quantity)
			.delivery(delivery)
			.buySatisfy(buySatisfy)
			.ageLimit(ageLimit)
			.size(size)
			.benefit(benefit)
			.build();
	}
}
