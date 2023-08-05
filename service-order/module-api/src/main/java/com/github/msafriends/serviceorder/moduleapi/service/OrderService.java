package com.github.msafriends.serviceorder.moduleapi.service;

import com.github.msafriends.modulecommon.exception.EntityNotFoundException;
import com.github.msafriends.modulecommon.exception.ErrorCode;
import com.github.msafriends.modulecommon.exception.InvalidStateException;
import com.github.msafriends.serviceorder.moduleapi.client.MemberServiceClient;
import com.github.msafriends.serviceorder.moduleapi.client.ProductServiceClient;
import com.github.msafriends.serviceorder.modulecore.domain.order.Order;
import com.github.msafriends.serviceorder.modulecore.domain.order.OrderStatus;
import com.github.msafriends.serviceorder.modulecore.dto.request.coupon.MemberCouponUseRequest;
import com.github.msafriends.serviceorder.modulecore.dto.request.order.ConfirmOrderRequest;
import com.github.msafriends.serviceorder.modulecore.dto.request.order.UpdateCartItemRequest;
import com.github.msafriends.serviceorder.modulecore.dto.request.order.UpdateStockRequest;
import com.github.msafriends.serviceorder.modulecore.dto.response.ListResponse;
import com.github.msafriends.serviceorder.modulecore.dto.response.coupon.MemberCouponResponse;
import com.github.msafriends.serviceorder.modulecore.dto.response.order.OrderPendingResponse;
import com.github.msafriends.serviceorder.modulecore.dto.response.order.OrderResponse;
import com.github.msafriends.serviceorder.modulecore.repository.OrderRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberServiceClient memberServiceClient;
    private final ProductServiceClient productServiceClient;

    public OrderResponse getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .map(OrderResponse::from)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ORDER_NOT_EXIST, orderId));
    }

    public List<OrderResponse> getOrderDetailsAwaitingAcceptance(Long memberId) {
        return orderRepository.findAllByMemberIdAndStatus(memberId, OrderStatus.AWAITING_ACCEPTANCE).stream()
                .map(OrderResponse::from)
                .toList();
    }

    public List<OrderResponse> getAllOrdersByMemberId(Long memberId) {
        return orderRepository.findAllByMemberId(memberId).stream()
                .map(OrderResponse::from)
                .toList();
    }

    public Optional<OrderPendingResponse> getOrderPendingByMemberId(Long memberId) {
        return orderRepository.findByMemberIdAndStatus(memberId, OrderStatus.PENDING)
                .map(OrderPendingResponse::from);
    }

    @Transactional
    public Long addCartItemToOrder(Long memberId, UpdateCartItemRequest request) {
        Order pendingOrder = getOrCreatePendingOrder(memberId);
        pendingOrder.updateCartItem(request);
        return orderRepository.save(pendingOrder).getId();
    }

    @Transactional
    public void confirmOrder(Long memberId, ConfirmOrderRequest request) {
        Order order = orderRepository.findByMemberIdAndStatus(memberId, OrderStatus.PENDING)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.PENDING_ORDER_NOT_EXIST));

        updateProductStocks(order, true);
        useMemberCoupons(memberId, request, order);

        order.confirm(request);
        orderRepository.save(order);
    }

    @Transactional
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    private void updateProductStocks(Order order, boolean decrement) {
        try {
            productServiceClient.bulkUpdateProductStocks(UpdateStockRequest.from(order.getCartItems(), decrement));
        } catch (FeignException ex) {
            throw new InvalidStateException(ErrorCode.NOT_ENOUGH_STOCK);
        }
    }

    private void useMemberCoupons(Long memberId, ConfirmOrderRequest request, Order order) {
        try {
            ListResponse<MemberCouponResponse> response = memberServiceClient.useCoupons(memberId, new MemberCouponUseRequest(request.getOrderCouponIds()));
            order.addCoupons(MemberCouponResponse.from(response.getValues()));
        } catch (FeignException ex) {
            updateProductStocks(order, false);
            throw new InvalidStateException(ErrorCode.ORDER_COUPON_NOT_EXIST, order.getId());
        }
    }

    private Order getOrCreatePendingOrder(Long memberId) {
        Optional<Order> pendingOrder = orderRepository.findByMemberIdAndStatus(memberId, OrderStatus.PENDING);
        return pendingOrder.orElseGet(() -> Order.createPendingOrderBuilder()
                .memberId(memberId)
                .build());
    }
}
