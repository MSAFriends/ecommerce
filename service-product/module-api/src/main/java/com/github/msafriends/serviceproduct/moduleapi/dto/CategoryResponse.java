package com.github.msafriends.serviceproduct.moduleapi.dto;

import java.util.ArrayList;
import java.util.List;

import com.github.msafriends.serviceproduct.modulecore.domain.category.Category;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class CategoryResponse {
    private Long categoryId;
    private String categoryName;
    private Long parentCategoryId;
    private List<CategoryResponse> childCategories = new ArrayList<>();

    @Builder
    public CategoryResponse(Long categoryId, String categoryName, Long parentCategoryId,
        List<CategoryResponse> childCategories) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.parentCategoryId = parentCategoryId;
        if(childCategories != null){
            this.childCategories = childCategories;
        }
    }

    public static CategoryResponse from(Category category){
        CategoryResponseBuilder builder = CategoryResponse.builder()
            .categoryId(category.getId())
            .categoryName(category.getCategoryName());
        if(category.getParentCategory() != null){
            return builder.parentCategoryId(category.getParentCategory().getId())
                .build();
        }
        return builder.build();
    }
}
