package com.github.msafriends.serviceproduct.modulecore.domain.product;

import com.github.msafriends.modulecommon.exception.ErrorCode;
import com.github.msafriends.modulecommon.exception.InvalidValueException;

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
			throw new InvalidValueException(ErrorCode.INVALID_PRICE_ERROR, "가격은 0보다 커야 합니다.");
		}

		if (salePrice < 0) {
			throw new InvalidValueException(ErrorCode.INVALID_PRICE_ERROR, "할인 가격은 0보다 커야 합니다.");
		}

		if (price < salePrice) {
			throw new InvalidValueException(ErrorCode.INVALID_PRICE_ERROR, "할인 가격은 가격보다 작아야 합니다.");
		}
	}
}
