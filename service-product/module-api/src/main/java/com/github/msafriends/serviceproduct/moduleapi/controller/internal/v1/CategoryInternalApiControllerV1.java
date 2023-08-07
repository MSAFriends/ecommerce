package com.github.msafriends.serviceproduct.moduleapi.controller.internal.v1;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.msafriends.serviceproduct.moduleapi.dto.CategoryRequest;
import com.github.msafriends.serviceproduct.moduleapi.dto.CategoryResponse;
import com.github.msafriends.serviceproduct.moduleapi.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/internal/v1")
@RequiredArgsConstructor
public class CategoryInternalApiControllerV1 {
    private final CategoryService categoryService;

    @PostMapping("/categories")
    public ResponseEntity<Void> registerCategory(@RequestBody CategoryRequest request){
        return ResponseEntity
            .created(
                URI.create(
                    "/api/internal/v1/categories/"
                        + categoryService.saveCategory(
                            request.getParentCategoryId(), request.toCategory())
                )
            ).build();
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryResponse>> readAllCategories(){
        return ResponseEntity.ok(categoryService.findAllCategories());
    }
}
