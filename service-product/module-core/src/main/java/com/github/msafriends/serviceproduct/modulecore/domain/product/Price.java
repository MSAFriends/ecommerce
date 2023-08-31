package com.github.msafriends.serviceproduct.modulecore.domain.product;

import com.github.msafriends.serviceproduct.modulecore.exception.ErrorCode;
import com.github.msafriends.serviceproduct.modulecore.exception.InvalidValueException;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Price {
	private int priceValue;
	private int salePriceValue;

	@Builder
	public Price(int priceValue, int salePriceValue) {
		validateSalePrice(priceValue, salePriceValue);
		this.priceValue = priceValue;
		this.salePriceValue = salePriceValue;
	}

	private void validateSalePrice(int price, int salePrice) {
		if (price < 0) {
			throw new InvalidValueException(ErrorCode.INVALID_PRICE_ERROR, price);
		}

		if (salePrice < 0) {
			throw new InvalidValueException(ErrorCode.INVALID_PRICE_ERROR, salePrice);
		}

		if (price < salePrice) {
			throw new InvalidValueException(ErrorCode.INVALID_PRICE_ERROR, salePrice);
		}
	}
}
