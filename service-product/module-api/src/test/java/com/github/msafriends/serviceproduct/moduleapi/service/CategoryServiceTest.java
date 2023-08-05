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
    class registerCategoryTest{

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
    class findAllCategoriesTest {
        @Test
        @DisplayName("전체 카테고리 조회 테스트")
        void readAllCategoriesTest() throws Exception {
            //given
            when(categoryRepository.findAllParentCategories())
                .thenReturn(CategoryFixture.createMainCategoryWithIdList(1, 3));
            when(categoryRepository.findAllByParentCategoryId(ArgumentMatchers.longThat(id -> id == 1)))
                .thenReturn(CategoryFixture.createSubCategoryListWithParentA(3, 5));
            when(categoryRepository.findAllByParentCategoryId(ArgumentMatchers.longThat(id -> id == 2)))
                .thenReturn(CategoryFixture.createSubCategoryListWithParentB(5, 7));
            when(categoryRepository.findAllByParentCategoryId(ArgumentMatchers.longThat(id -> id == 3)))
                .thenReturn(CategoryFixture.createMinorCategoryWithParentA(7, 10));
            when(categoryRepository.findAllByParentCategoryId(ArgumentMatchers.longThat(id -> id == 4)))
                .thenReturn(CategoryFixture.createMinorCategoryWithParentB(10, 13));
            when(categoryRepository.findAllByParentCategoryId(ArgumentMatchers.longThat(id -> id == 5)))
                .thenReturn(CategoryFixture.createMinorCategoryWithParentC(13, 16));
            when(categoryRepository.findAllByParentCategoryId(ArgumentMatchers.longThat(id -> id == 6)))
                .thenReturn(CategoryFixture.createMinorCategoryWithParentD(16, 18));
            when(categoryRepository.findAllByParentCategoryId(ArgumentMatchers.longThat(id -> id > 6)))
                .thenReturn(new ArrayList<>());
            //when
            List<CategoryResponse> allCategories = categoryService.findAllCategories();
            //then
            Assertions.assertThat(allCategories)
                .hasSize(2);
            Assertions.assertThat(
                allCategories
                    .get(0)
                    .getChildCategories())
                .hasSize(2);
            Assertions.assertThat(
                allCategories
                    .get(0)
                    .getChildCategories()
                    .get(0)
                    .getChildCategories())
                .hasSize(3);
            Assertions.assertThat(
                allCategories
                    .get(0)
                    .getChildCategories()
                    .get(0)
                    .getChildCategories()
                    .get(0)
                    .getChildCategories())
                .isEmpty();
        }

        @Test
        @DisplayName("카테고리 단건 조회 테스트")
        void findCategoryByIdTest() throws Exception {
            //given
            when(categoryRepository.findByIdOrThrow(ArgumentMatchers.longThat(id -> id >= 1 && id <=2))).thenReturn(
                SUB_CATEGORY_WITH_ID);
            //when
            Category category = categoryService.readCategoryById(2L);
            //then
            Assertions.assertThat(category.getId()).isEqualTo(SUB_CATEGORY_WITH_ID.getId());
        }
    }
}