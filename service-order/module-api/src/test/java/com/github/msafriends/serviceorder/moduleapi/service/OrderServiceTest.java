package com.github.msafriends.serviceorder.moduleapi.service;


import com.github.msafriends.serviceorder.moduleapi.client.MemberServiceClient;
import com.github.msafriends.serviceorder.moduleapi.client.ProductServiceClient;
import com.github.msafriends.serviceorder.modulecore.domain.order.Order;
import com.github.msafriends.serviceorder.modulecore.domain.order.OrderStatus;
import com.github.msafriends.serviceorder.modulecore.dto.request.coupon.MemberCouponUseRequest;
import com.github.msafriends.serviceorder.modulecore.dto.request.order.ConfirmOrderRequest;
import com.github.msafriends.serviceorder.modulecore.dto.response.ListResponse;
import com.github.msafriends.serviceorder.modulecore.dto.response.coupon.MemberCouponResponse;
import com.github.msafriends.serviceorder.modulecore.dto.response.order.OrderResponse;
import com.github.msafriends.serviceorder.modulecore.exception.EntityNotFoundException;
import com.github.msafriends.serviceorder.modulecore.exception.InvalidStateException;
import com.github.msafriends.serviceorder.modulecore.fixture.CartItemFixture;
import com.github.msafriends.serviceorder.modulecore.fixture.OrderFixture;
import com.github.msafriends.serviceorder.modulecore.repository.OrderRepository;
import feign.FeignException;
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
            Order order = OrderFixture.createConfirmedOrderWithId(orderId);
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
                    OrderFixture.createConfirmedOrderWithId(1L),
                    OrderFixture.createConfirmedOrderWithId(2L));

            when(orderRepository.findAllByMemberId(memberId)).thenReturn(orders);

            // when
            List<OrderResponse> result = orderService.getAllOrdersByMemberId(memberId);

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

            when(orderRepository.findByMemberIdAndStatus(memberId, OrderStatus.PENDING)).thenReturn(Optional.of(pendingOrder));
            when(orderRepository.save(pendingOrder)).thenReturn(pendingOrder);

            // when
            orderService.addCartItemToOrder(memberId, CartItemFixture.createUpdateCartItemRequestWithQuantity(quantity));

            // then
            assertThat(pendingOrder.getCartItems()).hasSize(1);
            assertThat(pendingOrder.getCartItems().get(0).getProduct().getQuantity()).isEqualTo(quantity);
        }
    }

    @Nested
    @DisplayName("주문 확정")
    class ConfirmOrderTest {
        @Test
        @DisplayName("PENDING 상태인 주문이 없으면 예외를 던진다.")
        void testConfirmOrderWithNoPendingOrder() {
            // given
            Long memberId = 1L;
            ConfirmOrderRequest request = OrderFixture.createConfirmOrderRequestWithRequest("테스트 요청사항");

            when(orderRepository.findByMemberIdAndStatus(memberId, OrderStatus.PENDING)).thenReturn(Optional.empty());

            // expected
            assertThatThrownBy(() -> orderService.confirmOrder(memberId, request))
                    .isInstanceOf(EntityNotFoundException.class);
        }

        @Test
        @DisplayName("재고가 충분하지 않은 경우 예외를 던진다.")
        void testConfirmOrderWithInsufficientStock() {
            // given
            Long memberId = 1L;
            Order pendingOrder = OrderFixture.createDefaultPendingOrder();
            ConfirmOrderRequest request = OrderFixture.createConfirmOrderRequestWithRequest("테스트 요청사항");

            when(orderRepository.findByMemberIdAndStatus(memberId, OrderStatus.PENDING)).thenReturn(Optional.of(pendingOrder));
            doThrow(FeignException.class).when(productServiceClient).bulkUpdateProductStocks(anyList());

            // expected
            assertThatThrownBy(() -> orderService.confirmOrder(memberId, request))
                    .isInstanceOf(InvalidStateException.class);
        }

        @Test
        @DisplayName("재고가 충분하나, 쿠폰 적용에 실패한 경우 예외를 던진다.")
        void testConfirmOrderWithCouponApplyFailure() {
            // given
            Long memberId = 1L;
            Order pendingOrder = OrderFixture.createDefaultPendingOrder();
            ConfirmOrderRequest request = OrderFixture.createConfirmOrderRequestWithRequest("테스트 요청사항");

            when(orderRepository.findByMemberIdAndStatus(memberId, OrderStatus.PENDING)).thenReturn(Optional.of(pendingOrder));
            doThrow(FeignException.class).when(memberServiceClient).useCoupons(eq(memberId), any(MemberCouponUseRequest.class));

            // expected
            assertThatThrownBy(() -> orderService.confirmOrder(memberId, request))
                    .isInstanceOf(InvalidStateException.class);
        }

        @Test
        @DisplayName("재고가 충분하고, 쿠폰 적용에 성공한 경우 주문을 확정한다.")
        void testConfirmOrder() {
            // given
            Long orderId = 1L;
            Long memberId = 1L;
            Order pendingOrder = OrderFixture.createPendingOrderWithId(orderId);
            ListResponse<MemberCouponResponse> memberCoupons = new ListResponse<>(0, List.of());
            ConfirmOrderRequest request = OrderFixture.createConfirmOrderRequestWithRequest("테스트 요청사항");

            when(orderRepository.findByMemberIdAndStatus(memberId, OrderStatus.PENDING)).thenReturn(Optional.of(pendingOrder));
            when(memberServiceClient.useCoupons(eq(memberId), any(MemberCouponUseRequest.class))).thenReturn(memberCoupons);
            when(orderRepository.save(pendingOrder)).thenReturn(pendingOrder);

            // when
            orderService.confirmOrder(memberId, request);

            // then
            assertThat(pendingOrder.getStatus()).isEqualTo(OrderStatus.AWAITING_ACCEPTANCE);

            verify(orderRepository).save(pendingOrder);
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