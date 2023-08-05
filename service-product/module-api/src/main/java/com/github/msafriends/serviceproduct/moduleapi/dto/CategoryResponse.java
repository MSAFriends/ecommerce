package com.github.msafriends.serviceproduct.moduleapi.dto;

import java.util.List;

import com.github.msafriends.serviceproduct.modulecore.domain.category.Category;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponse {
    private Long categoryId;
    private String categoryName;
    private List<CategoryResponse> childCategories;

    @Builder
    public CategoryResponse(Long categoryId, String categoryName, List<CategoryResponse> childCategories) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.childCategories = childCategories;
    }

    public static CategoryResponse fromCategory(Category category){
        return CategoryResponse.builder()
            .categoryId(category.getId())
            .categoryName(category.getCategoryName())
            .build();
    }
}
