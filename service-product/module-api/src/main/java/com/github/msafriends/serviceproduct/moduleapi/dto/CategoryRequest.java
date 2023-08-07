package com.github.msafriends.serviceproduct.moduleapi.dto;

import com.github.msafriends.serviceproduct.modulecore.domain.category.Category;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryRequest {
    private Long parentCategoryId;
    private String categoryName;

    @Builder
    public CategoryRequest(Long parentCategoryId, String categoryName) {
        this.parentCategoryId = parentCategoryId;
        this.categoryName = categoryName;
    }

    public Category toCategory(){
        return Category
            .builder()
            .categoryName(categoryName)
            .build();
    }
}
