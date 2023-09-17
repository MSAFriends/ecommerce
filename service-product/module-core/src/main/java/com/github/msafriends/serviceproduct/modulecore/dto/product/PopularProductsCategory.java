package com.github.msafriends.serviceproduct.modulecore.dto.product;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PopularProductsCategory {
    private Long categoryId;
    private List<PopularProductResponse> popularProducts = new ArrayList<>();

    @Builder
    public PopularProductsCategory(Long categoryId, List<PopularProductResponse> popularProducts) {
        this.categoryId = categoryId;
        this.popularProducts = popularProducts;
    }
}
