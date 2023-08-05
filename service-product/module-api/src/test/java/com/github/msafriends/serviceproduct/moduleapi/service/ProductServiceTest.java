package com.github.msafriends.serviceproduct.moduleapi.service;

import static com.github.msafriends.serviceproduct.common.fixture.product.ProductFixture.*;
import static org.mockito.Mockito.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.msafriends.serviceproduct.common.fixture.category.CategoryFixture;
import com.github.msafriends.serviceproduct.modulecore.domain.product.Product;
import com.github.msafriends.serviceproduct.modulecore.repository.CategoryRepository;
import com.github.msafriends.serviceproduct.modulecore.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

	@Mock
	ProductRepository productRepository;
	@Mock
	CategoryRepository categoryRepository;
	@InjectMocks
	ProductService productService;

	@Nested
	@DisplayName("상품 등록 테스트")
	class ProductRegisterTest{
		@Test
		@DisplayName("판매자는 카테고리 없는 상품을 등록할 수 있다.")
		void registerSuccessTestWithOutCategory() throws Exception {
			//given
			Product product = createProduct();
			when(productRepository.save(product)).thenReturn(createProductWithId(TEST_PRODUCT_ID));
			//when
			Long productId = productService.registerProduct(product);
			//then
			Assertions.assertThat(productId).isEqualTo(TEST_PRODUCT_ID);
		}
		@Test
		@DisplayName("판매자는 상품을 카테고리를 지정해 등록할 수 있다.")
		void registerSuccessTest() throws Exception {
			//given
			Product product = createProduct();
			when(productRepository.save(product)).thenReturn(createProductWithId(TEST_PRODUCT_ID));
			when(categoryRepository.findByIdOrThrow(CategoryFixture.MAIN_CATEGORY_ID_A)).thenReturn(CategoryFixture.createMainCategory(CategoryFixture.MAIN_CATEGORY_NAME));
			//when
			Long productId = productService.registerProduct(CategoryFixture.MAIN_CATEGORY_ID_A, product);
			//then
			Assertions.assertThat(productId).isEqualTo(TEST_PRODUCT_ID);
		}
	}
}
