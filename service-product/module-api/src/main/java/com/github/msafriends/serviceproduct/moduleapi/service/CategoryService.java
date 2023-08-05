package com.github.msafriends.serviceproduct.moduleapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.github.msafriends.serviceproduct.moduleapi.dto.CategoryResponse;
import com.github.msafriends.serviceproduct.modulecore.domain.category.Category;
import com.github.msafriends.serviceproduct.modulecore.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Long saveCategory(Long parentId, Category category){
        if(parentId != null){
            category.assignParentCategory(categoryRepository.findByIdOrThrow(parentId));
        }
        return categoryRepository.save(category).getId();
    }

    public List<CategoryResponse> findAllCategories(){
        List<Category> allCategories = categoryRepository.findAll();
        Map<Long, CategoryResponse> categoryResponseMap = allCategories.stream()
            .collect(Collectors.toMap(
                Category::getId,
                CategoryResponse::from));
        List<CategoryResponse> rootCategoryResponses = new ArrayList<>();
        allCategories.forEach(category -> {
            CategoryResponse categoryResponse = categoryResponseMap.get(category.getId());
            if (category.getParentCategory() == null) {
                rootCategoryResponses.add(categoryResponse);
            } else {
                var parent = categoryResponseMap.get(category.getParentCategory().getId());
                parent.getChildCategories().add(categoryResponse);
            }
        });
        return rootCategoryResponses;
    }
}
