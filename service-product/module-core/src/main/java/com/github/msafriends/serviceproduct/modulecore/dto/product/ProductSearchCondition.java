package com.github.msafriends.serviceproduct.modulecore.dto.product;

import com.github.msafriends.serviceproduct.modulecore.domain.product.AgeLimit;
import com.github.msafriends.serviceproduct.modulecore.exception.ErrorCode;
import com.github.msafriends.serviceproduct.modulecore.exception.InvalidValueException;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductSearchCondition {
    private String keyword;
    private Integer minPrice;
    private Integer maxPrice;
    private SatisfactionOrder satisfactionOrder;
    private DiscountOrder discountOrder;
    private AgeLimit ageLimit;
    private Long categoryId;

    @Builder
    private ProductSearchCondition(String keyword, Integer minPrice, Integer maxPrice, SatisfactionOrder satisfactionOrder, DiscountOrder discountOrder,
        AgeLimit ageLimit, Long categoryId) {
        validatePriceRange(minPrice, maxPrice);
        this.keyword = keyword;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.satisfactionOrder = satisfactionOrder;
        this.discountOrder = discountOrder;
        this.ageLimit = ageLimit;
        this.categoryId = categoryId;
    }

    public static ProductSearchCondition of(final String keyword, final Integer minPrice, final Integer maxPrice, final SatisfactionOrder satisfactionOrder, final DiscountOrder discountOrder,
        final AgeLimit ageLimit, final Long categoryId){
        return ProductSearchCondition.builder()
            .keyword(keyword)
            .minPrice(minPrice)
            .maxPrice(maxPrice)
            .ageLimit(ageLimit)
            .categoryId(categoryId)
            .satisfactionOrder(satisfactionOrder)
            .discountOrder(discountOrder)
            .build();

    }

    private void validatePriceRange(Integer minPrice, Integer maxPrice){
        if(minPrice != null && maxPrice != null){
            if(minPrice < 0){
                throw new InvalidValueException(ErrorCode.INVALID_PRICE_ERROR, minPrice);
            }
            if(maxPrice < 0){
                throw new InvalidValueException(ErrorCode.INVALID_PRICE_ERROR, maxPrice);
            }
            if(minPrice > maxPrice){
                throw new InvalidValueException(ErrorCode.INVALID_PRICE_RANGE, minPrice, maxPrice);
            }
        }
    }
}
