package com.github.msafriends.serviceproduct.moduleapi.service;

import java.util.ArrayList;
import java.util.List;

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
        List<Category> rootParents = categoryRepository.findAllParentCategories();
        List<CategoryResponse> categoryReponses = rootParents
            .stream()
            .map(CategoryResponse::fromCategory)
            .toList();
        categoryReponses.forEach(this::getChildCategories);
        return categoryReponses;
    }

    private void getChildCategories(CategoryResponse parentCategory){
        List<CategoryResponse> childs = categoryRepository
            .findAllByParentCategoryId(parentCategory.getCategoryId())
            .stream()
            .map(CategoryResponse::fromCategory)
            .toList();
        if(!childs.isEmpty()) {
            parentCategory.setChildCategories(childs);
        }else {
            parentCategory.setChildCategories(new ArrayList<>());
        }
        childs.forEach(this::getChildCategories);
    }

    public Category readCategoryById(Long id){
        return categoryRepository.findByIdOrThrow(id);
    }


}
