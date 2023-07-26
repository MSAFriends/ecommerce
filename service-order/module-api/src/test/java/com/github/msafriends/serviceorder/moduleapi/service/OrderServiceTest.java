package com.github.msafriends.serviceorder.moduleapi.service;

import com.github.msafriends.serviceorder.moduleapi.client.MemberServiceClient;
import com.github.msafriends.serviceorder.moduleapi.client.ProductServiceClient;
import com.github.msafriends.serviceorder.moduleapi.repository.OrderRepository;
import com.github.msafriends.serviceorder.modulecore.domain.order.Order;
import com.github.msafriends.serviceorder.modulecore.dto.request.order.OrderRequest;
import com.github.msafriends.serviceorder.modulecore.dto.response.order.OrderResponse;
import com.github.msafriends.serviceorder.modulecore.dto.response.order.ProductResponse;
import com.github.msafriends.serviceorder.modulecore.fixture.OrderCouponFixture;
import com.github.msafriends.serviceorder.modulecore.fixture.OrderFixture;
import com.github.msafriends.serviceorder.modulecore.fixture.CartItemFixture;
import com.github.msafriends.serviceorder.modulecore.fixture.ProductFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private MemberServiceClient memberServiceClient;

    @Mock
    private ProductServiceClient productServiceClient;

    @InjectMocks
    private OrderService orderService;

    @Nested
    @DisplayName("주문 조회")
    class GetOrderTest {
        @Test
        @DisplayName("주문을 하나 조회한다.")
        void testGetOrder() {
            // given
            Long orderId = 1L;
            Order order = OrderFixture.createDefaultOrderWithId(orderId);
            OrderResponse orderResponse = OrderFixture.createOrderResponse(order);

            when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

            // when
            OrderResponse result = orderService.getOrder(orderId);

            // then
            assertThat(result.getId()).isEqualTo(orderResponse.getId());
        }

        @Test
        @DisplayName("멤버 ID로 주문을 여러 건 조회한다.")
        void testGetOrdersByMemberId() {
            // given
            Long memberId = 1L;
            List<Order> orders = List.of(
                    OrderFixture.createDefaultOrderWithId(1L),
                    OrderFixture.createDefaultOrderWithId(2L));

            when(orderRepository.findAllByMemberId(memberId)).thenReturn(orders);

            // when
            List<OrderResponse> result = orderService.getOrdersByMemberId(memberId);

            // then
            assertThat(result).hasSize(2);
        }
    }

    @Nested
    @DisplayName("주문을 생성한다.")
    class CreateOrderTest {

        @Test
        @DisplayName("존재하지 않는 상품 ID로 주문을 하면 실패한다.")
        void testCreateOrderWithNonExistingProductId() {
            // given
            Long memberId = 1L;
            Long nonExistingProductId = 2L;
            OrderRequest request = OrderFixture.createOrderRequestWithCartItems(
                    List.of(CartItemFixture.createCartItemRequestWithProductId(nonExistingProductId)));

            when(productServiceClient.getProduct(nonExistingProductId)).thenReturn(null);

            // expected
            assertThatThrownBy(() -> orderService.createOrder(memberId, request))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("재고가 부족한 경우에 주문을 하면 실패한다.")
        void testCreateOrderWithInsufficientStock() {
            // given
            Long memberId = 1L;
            int currentQuantity = 0;
            OrderRequest request = OrderFixture.createOrderRequestWithCartItems(
                    List.of(CartItemFixture.createCartItemRequestWithQuantity(currentQuantity)));

            when(productServiceClient.getProduct(anyLong())).thenReturn(mock(ProductResponse.class));

            // expected
            assertThatThrownBy(() -> orderService.createOrder(memberId, request))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("quantity must be positive");
        }

        @Test
        @DisplayName("존재하지 않는 쿠폰 ID로 주문을 하면 실패한다.")
        void testCreateOrderWithNonExistingCouponId() {
            // given
            Long memberId = 1L;
            Long existingCouponId = 1L;
            Long nonExistingCouponId = 2L;
            OrderRequest request = OrderFixture.createOrderRequestWithOrderCouponIds(List.of(nonExistingCouponId));

            when(productServiceClient.getProduct(anyLong())).thenReturn(
                    ProductFixture.createDefaultProductResponseWithQuantity(1));
            when(memberServiceClient.getAvailableCouponsByMemberId(memberId)).thenReturn(List.of(
                    OrderCouponFixture.createFixedDiscountOrderCouponResponseWithId(existingCouponId)));

            // expected
            assertThatThrownBy(() -> orderService.createOrder(memberId, request))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessage(String.format("Coupon with ID %d not found", nonExistingCouponId));
        }

        @Test
        @DisplayName("성공적으로 주문을 생성한다.")
        void testCreateOrder() {
            // given
            Long memberId = 1L;
            Long orderId = 1L;
            Long existingProductId = 1L;
            Long existingCouponId = 1L;
            Order order = OrderFixture.createDefaultOrderWithId(orderId);
            OrderRequest request = OrderFixture.createOrderRequestWithCartItems(
                    List.of(CartItemFixture.createCartItemRequestWithProductId(existingProductId)));

            when(productServiceClient.getProduct(existingProductId)).thenReturn(
                    ProductFixture.createDefaultProductResponseWithQuantity(1));
            when(memberServiceClient.getAvailableCouponsByMemberId(memberId)).thenReturn(List.of(
                    OrderCouponFixture.createFixedDiscountOrderCouponResponseWithId(existingCouponId)));
            when(orderRepository.save(any(Order.class))).thenReturn(order);

            // when
            Long result = orderService.createOrder(memberId, request);

            // expected
            assertThat(result).isEqualTo(orderId);
        }
    }

    @Nested
    @DisplayName("주문 삭제")
    class DeleteOrderTest {
        @Test
        @DisplayName("주문을 삭제한다.")
        void testDeleteOrder() {
            // given
            Long orderId = 1L;
            doNothing().when(orderRepository).deleteById(orderId);

            // when
            orderService.deleteOrder(orderId);

            // then
            verify(orderRepository).deleteById(orderId);
        }
    }
}