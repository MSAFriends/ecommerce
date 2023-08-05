package com.github.msafriends.serviceproduct.moduleapi.controller.internal.v1;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.msafriends.serviceproduct.common.AcceptanceTest;
import com.github.msafriends.serviceproduct.common.fixture.category.CategoryFixture;
import com.github.msafriends.serviceproduct.moduleapi.service.CategoryService;
import com.github.msafriends.serviceproduct.modulecore.domain.category.Category;

class CategoryInternalApiControllerV1Test extends AcceptanceTest {
    @MockBean
    CategoryService categoryService;
    @Autowired
    ObjectMapper objectMapper;
    @BeforeEach
    void setup(){
        when(categoryService.saveCategory(anyLong(), any(Category.class))).thenReturn(1L);
    }

    @Test
    @DisplayName("카테고리 등록 API테스트")
    void registerCategoryApiTest() throws Exception {
        //given
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post("/api/internal/v1/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(CategoryFixture.createCategoryRequest(1L))));
        //expected
        action
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(redirectedUrl("/api/internal/v1/categories/" + 1L));
    }

    @Test
    @DisplayName("전체 카테고리 조회 테스트")
    void readAllCategoriesTest() throws Exception {
        //given
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get("/api/internal/v1/categories"));
        //expected
        action.andDo(print())
            .andExpect(status().isOk());
    }
}