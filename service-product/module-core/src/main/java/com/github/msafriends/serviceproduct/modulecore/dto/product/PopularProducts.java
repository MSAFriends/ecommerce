package com.github.msafriends.serviceproduct.modulecore.dto.product;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PopularProducts {
    private List<PopularProductResponse> popularProductList = new ArrayList<>();

    @Builder
    public PopularProducts(List<PopularProductResponse> popularProducts) {
        this.popularProductList = popularProducts;
    }
}
