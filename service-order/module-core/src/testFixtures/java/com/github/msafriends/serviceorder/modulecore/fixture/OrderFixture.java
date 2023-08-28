package com.github.msafriends.serviceorder.modulecore.fixture;

import com.github.msafriends.serviceorder.modulecore.domain.order.Order;
import com.github.msafriends.serviceorder.modulecore.domain.order.OrderStatus;
import com.github.msafriends.serviceorder.modulecore.domain.order.Recipient;
import com.github.msafriends.serviceorder.modulecore.dto.request.order.ConfirmOrderRequest;
import com.github.msafriends.serviceorder.modulecore.dto.request.order.RecipientRequest;
import com.github.msafriends.serviceorder.modulecore.dto.response.order.OrderResponse;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

public class OrderFixture {
    private static final Long DEFAULT_MEMBER_ID = 1L;
    private static final String DEFAULT_REQUEST = "테스트 요청사항";
    private static final Recipient DEFAULT_RECIPIENT = RecipientFixture.createDefaultRecipient();
    private static final OrderStatus DEFAULT_STATUS = OrderStatus.PENDING;

    private static Order.PendingOrderBuilder createDefaultPendingOrderBuilder() {
        return Order.createPendingOrderBuilder()
                .memberId(DEFAULT_MEMBER_ID);
    }

    public static Order createDefaultPendingOrder() {
        return createDefaultPendingOrderBuilder()
                .build();
    }

    public static Order createPendingOrderWithId(Long id) {
        Order order = createDefaultPendingOrder();
        ReflectionTestUtils.setField(order, "id", id);
        return order;
    }

    public static Order.ConfirmedOrderBuilder createDefaultConfirmedOrderBuilder() {
        return Order.createConfirmedOrderBuilder()
                .memberId(DEFAULT_MEMBER_ID);
    }

    public static Order createDefaultConfirmedOrder() {
        return createDefaultConfirmedOrderBuilder()
                .request(DEFAULT_REQUEST)
                .recipient(DEFAULT_RECIPIENT)
                .build();
    }

    public static Order createConfirmedOrderWithId(Long id) {
        Order order = createDefaultConfirmedOrder();
        ReflectionTestUtils.setField(order, "id", id);
        return order;
    }

    public static OrderResponse createOrderResponse(Order order) {
        return OrderResponse.from(order);
    }

    private static ConfirmOrderRequest.ConfirmOrderRequestBuilder createDefaultConfirmOrderRequest() {
        return ConfirmOrderRequest.builder()
                .request(DEFAULT_REQUEST)
                .recipient(RecipientFixture.createDefaultRecipientRequest())
                .orderCouponIds(List.of(OrderCouponFixture.DEFAULT_COUPON_ID));
    }

    public static ConfirmOrderRequest createConfirmOrderRequestWithOrderCouponIds(List<Long> orderCouponIds) {
        return createDefaultConfirmOrderRequest()
                .orderCouponIds(orderCouponIds)
                .build();
    }

    public static ConfirmOrderRequest createConfirmOrderRequestWithRecipient(RecipientRequest recipient) {
        return createDefaultConfirmOrderRequest()
                .recipient(recipient)
                .build();
    }

    public static ConfirmOrderRequest createConfirmOrderRequestWithRequest(String request) {
        return createDefaultConfirmOrderRequest()
                .request(request)
                .build();
    }
}
