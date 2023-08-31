package com.github.msafriends.serviceproduct.moduleapi.controller.internal.v1;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.msafriends.serviceproduct.common.AcceptanceTest;
import com.github.msafriends.serviceproduct.modulecore.dto.productimage.ProductImageResponse;
import com.github.msafriends.serviceproduct.moduleapi.service.ProductImageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.github.msafriends.serviceproduct.common.fixture.product.ProductFixture.*;
import static com.github.msafriends.serviceproduct.common.fixture.product.ProductImageFixture.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@ActiveProfiles(value = "prod")
class ProductImageExternalApiControllerV1Test extends AcceptanceTest {

    @MockBean
    ProductImageService productImageService;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    @DisplayName("상품 이미지 등록 API 테스트")
    void productImageRegisterAPITest() throws Exception {
        //given
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products/1/images")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createProductImage(createProduct()))));
        //expected
        action
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("상품 이미지 목록 조회 API 테스트")
    void productImagesGetAPITest() throws Exception {

        when(productImageService.getProductImages(1L))
                .thenReturn(List.of(ProductImageResponse.from(createProductImage(createProduct()))));

        //given
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/1/images")
                .contentType(MediaType.APPLICATION_JSON));

        action
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0]").exists());
    }

    @Test
    @DisplayName("상품 이미지 삭제 API 테스트")
    void productImageDeleteAPITest() throws Exception {
        //given
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/products/1/images/1")
                .contentType(MediaType.APPLICATION_JSON));
        //expected
        action
                .andDo(print())
                .andExpect(status().isOk());
    }
}
