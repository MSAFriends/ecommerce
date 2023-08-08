package com.github.msafriends.serviceproduct.moduleapi.service;

import static com.github.msafriends.serviceproduct.common.fixture.product.ProductFixture.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.msafriends.modulecommon.exception.ErrorCode;
import com.github.msafriends.modulecommon.exception.product.NotEnoughStockException;
import com.github.msafriends.serviceproduct.common.fixture.category.CategoryFixture;
import com.github.msafriends.serviceproduct.moduleapi.dto.UpdateStockRequest;
import com.github.msafriends.serviceproduct.moduleapi.service.product.DefaultProductServiceImpl;
import com.github.msafriends.serviceproduct.modulecore.domain.product.Product;
import com.github.msafriends.serviceproduct.modulecore.repository.CategoryRepository;
import com.github.msafriends.serviceproduct.modulecore.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class DefaultProductServiceImplTest {

	private static final Long RANDOM_ID = 3L;

	@Mock
	ProductRepository productRepository;
	@Mock
	CategoryRepository categoryRepository;
	@InjectMocks
    DefaultProductServiceImpl defaultProductServiceImpl;

	@Nested
	@DisplayName("상품 등록 테스트")
	class ProductRegisterTest{
		@Test
		@DisplayName("판매자는 카테고리 없는 상품을 등록할 수 있다.")
		void registerSuccessTestWithOutCategory() throws Exception {
			//given
			Product product = createProduct();
			when(productRepository.save(product)).thenReturn(createProductWithIdAndQuantity(TEST_PRODUCT_ID, 10));
			//when
			Long productId = defaultProductServiceImpl.registerProduct(product);
			//then
			Assertions.assertThat(productId).isEqualTo(TEST_PRODUCT_ID);
		}
		@Test
		@DisplayName("판매자는 상품을 카테고리를 지정해 등록할 수 있다.")
		void registerSuccessTest() throws Exception {
			//given
			Product product = createProduct();
			when(productRepository.save(product)).thenReturn(createProductWithIdAndQuantity(TEST_PRODUCT_ID, 10));
			when(categoryRepository.findByIdOrThrow(CategoryFixture.MAIN_CATEGORY_ID_A)).thenReturn(CategoryFixture.createMainCategory(CategoryFixture.MAIN_CATEGORY_NAME));
			//when
			Long productId = defaultProductServiceImpl.registerProduct(CategoryFixture.MAIN_CATEGORY_ID_A, product);
			//then
			Assertions.assertThat(productId).isEqualTo(TEST_PRODUCT_ID);
		}
	}

	@Nested
	@DisplayName("상품 정보 갱신 테스트")
	class UpdateProductTest{
		@Test
		@DisplayName("주문 정보를 바탕으로 상품의 재고를 증감할 수 있다.")
		void updateStockTest() throws Exception {
			//given
			List<UpdateStockRequest> updateStockRequestList = createUpdateStockRequestList(3, -4, -5, -6);
			List<Product> orderedProduct = createOrderedProduct(10, 3);
			when(productRepository.findProductsByIdIn(Arrays.asList(1L,2L,3L)))
				.thenReturn(orderedProduct);
			//when
			assertDoesNotThrow(() -> defaultProductServiceImpl.updateStocks(updateStockRequestList));
		}

		@Test
		@DisplayName("주문 수량이 재고 수량 보다 많은 경우 예외를 발생시킨다.")
		void invalidOrderQuantityTest() throws Exception {
			//given
			List<UpdateStockRequest> updateStockRequestList = createUpdateStockRequestList(3, -3, -12, -1);
			when(productRepository.findProductsByIdIn(Arrays.asList(1L,2L,3L))).thenReturn(createOrderedProduct(10, 3));
			//when
			NotEnoughStockException notEnoughStockException = assertThrows(NotEnoughStockException.class,
				() -> defaultProductServiceImpl.updateStocks(updateStockRequestList));
			//then
			System.out.println(notEnoughStockException.getDetail());
			Assertions.assertThat(notEnoughStockException.getErrorCode()).isEqualTo(ErrorCode.INVALID_ORDER_QUANTITY);
		}
	}

	@Nested
	@DisplayName("상품 조회 테스트")
	class FindProductTest{
		@Test
		@DisplayName("다른 마이크로 서비스는 판매자 id를 이용해 관련된 상품을 조회할 수 있다.")
		void findProductBySellerId() throws Exception {
			//given
			List<Product> list = new ArrayList<>();
			for (int i = 0; i < 4; i++) {
				list.add(createProductWithSellerId((RANDOM_ID)));
			}
			when(productRepository.findProductsBySellerId(RANDOM_ID)).thenReturn(list);
			//when
			List<Product> foundProducts = defaultProductServiceImpl.readProductsBySellerId(RANDOM_ID);
			//then
			Assertions.assertThat(foundProducts).hasSize(4);
			Assertions.assertThat(foundProducts.get(0).getSellerId()).isEqualTo(RANDOM_ID);
		}

		@Test
		@DisplayName("카테고리 id로 상품 조회")
		void findProductsByCategoryTest() throws Exception {
			//given
			List<Product> list = new ArrayList<>();
			for (int i = 0; i < 4; i++) {
				list.add(createProductWithCategoryId(RANDOM_ID));
			}
			when(productRepository.findProductByCategoryId(RANDOM_ID)).thenReturn(list);
			//when
			List<Product> foundProducts = defaultProductServiceImpl.readProductsByCategoryId(RANDOM_ID);
			//then
			Assertions.assertThat(foundProducts).hasSize(4);
			Assertions.assertThat(foundProducts.get(0).getCategory().getId()).isEqualTo(RANDOM_ID);
		}
	}
}
