package com.github.msafriends.serviceproduct.moduleapi.controller.internal.v1;

import static com.github.msafriends.serviceproduct.common.fixture.product.ProductFixture.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.msafriends.serviceproduct.moduleapi.service.product.DefaultProductServiceImpl;

import com.github.msafriends.serviceproduct.common.AcceptanceTest;

@ActiveProfiles(value = "test")
@Transactional
class ProductControllerTest extends AcceptanceTest {
	@MockBean
	DefaultProductServiceImpl defaultProductServiceImpl;
	@Autowired
	ObjectMapper objectMapper;

	@BeforeEach
	void setup(){
		when(defaultProductServiceImpl.registerProduct(any())).thenReturn(1L);
	}

	@Test
	@DisplayName("상품 등록 API 테스트")
	void productRegisterAPITest() throws Exception {
		//given
		ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post("/api/internal/v1/products")
			.header("Seller-Id", SELLER_ID)
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(createProductRequest())));
		//expected
		action
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(redirectedUrl("/api/internal/v1/products/" + 1L));
	}

	@Test
	@DisplayName("주문 재고 증감 API 테스트")
	void bulkUpdateStockTest() throws Exception {
		//given
		ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post("/api/internal/v1/products/stocks")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(createUpdateStockRequestList(3, -2, -3, -5)))
		);
		action.andExpect(status().isOk());
	}
}
