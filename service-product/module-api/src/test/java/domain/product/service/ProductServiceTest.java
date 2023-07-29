package domain.product.service;

import static common.fixture.product.ProductFixture.*;
import static org.mockito.Mockito.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.msafriends.serviceproduct.moduleapi.repository.CategoryRepository;
import com.github.msafriends.serviceproduct.moduleapi.repository.ProductRepository;
import com.github.msafriends.serviceproduct.moduleapi.service.ProductService;

import com.github.msafriends.serviceproduct.modulecore.domain.product.Product;
import common.fixture.category.CategoryFixture;

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
			when(categoryRepository.findByIdOrThrow(CategoryFixture.TEST_CATEGORY_ID)).thenReturn(CategoryFixture.createMainCategory());
			//when
			Long productId = productService.registerProduct(CategoryFixture.TEST_CATEGORY_ID, product);
			//then
			Assertions.assertThat(productId).isEqualTo(TEST_PRODUCT_ID);
		}
	}
}
