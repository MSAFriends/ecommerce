package com.github.msafriends.serviceproduct.moduleapi.service;

import org.springframework.stereotype.Service;

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
}
