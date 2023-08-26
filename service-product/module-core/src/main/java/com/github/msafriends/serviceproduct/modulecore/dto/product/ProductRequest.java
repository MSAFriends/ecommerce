package com.github.msafriends.serviceproduct.modulecore.dto.product;

import com.github.msafriends.serviceproduct.modulecore.domain.product.AgeLimit;
import com.github.msafriends.serviceproduct.modulecore.domain.product.Benefit;
import com.github.msafriends.serviceproduct.modulecore.domain.product.Price;
import com.github.msafriends.serviceproduct.modulecore.domain.product.Product;
import com.github.msafriends.serviceproduct.modulecore.domain.product.Size;
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
	private int price;
	private int salesPrice;
	@Min(value = 0)
	@NotNull
	private Integer quantity;
	@NotNull
	private String delivery;
	private Integer buySatisfy;
	private Integer discount;
	private Integer mileage;
	@NotNull
	private AgeLimit ageLimit;
	private Size size;
	private Long categoryId;

	@Builder
	public ProductRequest(Long sellerId, Long code, String name, int price, int salesPrice, Integer quantity,
		String delivery, Integer buySatisfy, Integer discount, Integer mileage, AgeLimit ageLimit, Size size,
		Long categoryId) {
		this.sellerId = sellerId;
		this.code = code;
		this.name = name;
		this.price = price;
		this.salesPrice = salesPrice;
		this.quantity = quantity;
		this.delivery = delivery;
		this.buySatisfy = buySatisfy;
		this.discount = discount;
		this.mileage = mileage;
		this.ageLimit = ageLimit;
		this.size = size;
		this.categoryId = categoryId;
	}

	public Product toEntity(Long sellerId){
		return Product.builder()
			.sellerId(sellerId)
			.name(name)
			.code(code)
			.price(Price
				.builder().
				priceValue(price)
				.salePriceValue(salesPrice)
				.build())
			.quantity(quantity)
			.delivery(delivery)
			.buySatisfy(buySatisfy)
			.ageLimit(ageLimit)
			.size(size)
			.benefit(Benefit.builder()
				.discount(discount)
				.mileage(mileage)
				.build())
			.build();
	}
}
