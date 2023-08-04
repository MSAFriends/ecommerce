package com.github.msafriends.serviceproduct.moduleapi.service;

import static com.github.msafriends.serviceproduct.common.fixture.category.CategoryFixture.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.msafriends.serviceproduct.common.fixture.category.CategoryFixture;
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
    class registerCategoryTest{
        @Test
        @DisplayName("최상위 Root부모 카테고리 등록 테스트")
        void rootCategoryRegisterTest() throws Exception {
            //given
            Category category = createMainCategory();
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
        Category subCategory = createSubCategory();
        Category minorCategory = createMinorCategory();
        when(categoryRepository.save(subCategory)).thenReturn(SUB_CATEGORY_WITH_ID);
        when(categoryRepository.save(minorCategory)).thenReturn(MINOR_CATEGORY_WITH_ID);
        //when
        Long subCategoryId = categoryService.saveCategory(TEST_MAIN_CATEGORY_ID, subCategory);
        Long minorCategoryId = categoryService.saveCategory(TEST_SUB_CATEGORY_ID, minorCategory);
        //then
        Assertions.assertThat(subCategoryId).isEqualTo(SUB_CATEGORY_WITH_ID.getId());
        Assertions.assertThat(minorCategoryId).isEqualTo(MINOR_CATEGORY_WITH_ID.getId());
    }
}