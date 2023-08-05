package com.github.msafriends.serviceproduct.moduleapi.service;

import static com.github.msafriends.serviceproduct.common.fixture.category.CategoryFixture.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.msafriends.serviceproduct.common.fixture.category.CategoryFixture;
import com.github.msafriends.serviceproduct.moduleapi.dto.CategoryResponse;
import com.github.msafriends.serviceproduct.modulecore.domain.category.Category;
import com.github.msafriends.serviceproduct.modulecore.repository.CategoryRepository;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    @Mock
    CategoryRepository categoryRepository;
    @InjectMocks
    CategoryService categoryService;

    @Nested
    @DisplayName("카테고리 등록 테스트")
    class RegisterCategoryTest{

        @Test
        @DisplayName("최상위 Root부모 카테고리 등록 테스트")
        void rootCategoryRegisterTest() throws Exception {
            //given
            Category category = createMainCategory(MAIN_CATEGORY_NAME);
            when(categoryRepository.save(category)).thenReturn(MAIN_CATEGORY_WITH_ID);
            //when
            Long savedId = categoryService.saveCategory(null, category);
            //then
            Assertions.assertThat(savedId).isEqualTo(MAIN_CATEGORY_WITH_ID.getId());
        }
    }

    @Test
    @DisplayName("자식 카테고리 등록 테스트")
    void registerChildCategoryTest() throws Exception {
        //given
        Category subCategory = createSubCategory(SUB_CATEGORY_NAME, MAIN_CATEGORY_ID_A);
        Category minorCategory = createMinorCategory(MINOR_CATEGORY_NAME, SUB_CATEGORY_ID_A, MAIN_CATEGORY_ID_A);
        when(categoryRepository.save(subCategory)).thenReturn(SUB_CATEGORY_WITH_ID);
        when(categoryRepository.save(minorCategory)).thenReturn(MINOR_CATEGORY_WITH_ID);
        //when
        Long subCategoryId = categoryService.saveCategory(MAIN_CATEGORY_ID_A, subCategory);
        Long minorCategoryId = categoryService.saveCategory(SUB_CATEGORY_ID_A, minorCategory);
        //then
        Assertions.assertThat(subCategoryId).isEqualTo(SUB_CATEGORY_WITH_ID.getId());
        Assertions.assertThat(minorCategoryId).isEqualTo(MINOR_CATEGORY_WITH_ID.getId());
    }

    @Nested
    @DisplayName("카테고리 조회 테스트")
    class FindAllCategoriesTest {

        @Test
        @DisplayName("전체 카테고리 조회 테스트")
        void findAllCategories2() throws Exception {
            //given
            when(categoryRepository.findAll()).thenReturn(CategoryFixture.createEntireCategoryTree());
            //when
            List<CategoryResponse> allCategories2 = categoryService.findAllCategories();
            //then
            Assertions.assertThat(allCategories2)
                .hasSize(2);
            Assertions.assertThat(
                    allCategories2
                        .get(0)
                        .getChildCategories())
                .hasSize(2);
            Assertions.assertThat(
                    allCategories2
                        .get(0)
                        .getChildCategories()
                        .get(0)
                        .getChildCategories())
                .hasSize(3);
            Assertions.assertThat(
                    allCategories2
                        .get(0)
                        .getChildCategories()
                        .get(0)
                        .getChildCategories()
                        .get(0)
                        .getChildCategories())
                .isEmpty();
        }
    }
}