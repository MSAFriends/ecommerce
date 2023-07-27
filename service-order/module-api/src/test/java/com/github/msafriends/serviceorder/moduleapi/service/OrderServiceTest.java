package com.github.msafriends.serviceorder.moduleapi.service;

import com.github.msafriends.serviceorder.moduleapi.repository.OrderRepository;
import com.github.msafriends.serviceorder.modulecore.domain.order.Order;
import com.github.msafriends.serviceorder.modulecore.dto.response.order.OrderResponse;
import com.github.msafriends.serviceorder.modulecore.fixture.CartItemFixture;
import com.github.msafriends.serviceorder.modulecore.fixture.OrderFixture;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

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
            Order order = OrderFixture.createPendingOrderWithId(orderId);
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
                    OrderFixture.createPendingOrderWithId(1L),
                    OrderFixture.createPendingOrderWithId(2L));

            when(orderRepository.findAllByMemberId(memberId)).thenReturn(orders);

            // when
            List<OrderResponse> result = orderService.getOrdersByMemberId(memberId);

            // then
            assertThat(result).hasSize(2);
        }
    }

    @Nested
    @DisplayName("주문 변경")
    class UpdateOrderTest {
        @Test
        @DisplayName("PENDING 상태인 주문에 대해, 주문 항목의 수량을 변경한다.")
        void testUpdateOrderWithPendingOrder() {
            // given
            int quantity = 5;
            Long memberId = 1L;
            Order pendingOrder = OrderFixture.createDefaultPendingOrder();
            when(orderRepository.findPendingOrder(memberId)).thenReturn(Optional.of(pendingOrder));
            when(orderRepository.save(pendingOrder)).thenReturn(pendingOrder);

            // when
            orderService.addCartItemToOrder(memberId, CartItemFixture.createUpdateCartItemRequestWithQuantity(quantity));

            // then
            assertThat(pendingOrder.getCartItems()).hasSize(1);
            assertThat(pendingOrder.getCartItems().get(0).getProduct().getQuantity()).isEqualTo(quantity);
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