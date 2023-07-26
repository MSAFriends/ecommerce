package com.github.msafriends.serviceorder.modulecore.fixture;

import com.github.msafriends.serviceorder.modulecore.domain.order.Order;
import com.github.msafriends.serviceorder.modulecore.domain.order.OrderStatus;
import com.github.msafriends.serviceorder.modulecore.domain.order.Recipient;
import com.github.msafriends.serviceorder.modulecore.dto.request.order.CartItemRequest;
import com.github.msafriends.serviceorder.modulecore.dto.request.order.OrderRequest;
import com.github.msafriends.serviceorder.modulecore.dto.request.order.RecipientRequest;
import com.github.msafriends.serviceorder.modulecore.dto.response.order.OrderResponse;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

public class OrderFixture {
    private static final Long DEFAULT_MEMBER_ID = 1L;
    private static final String DEFAULT_REQUEST = "테스트 요청사항";
    private static final Recipient DEFAULT_RECIPIENT = RecipientFixture.createDefaultRecipient();
    private static final OrderStatus DEFAULT_STATUS = OrderStatus.PENDING;

    private static Order.OrderBuilder createDefaultOrderBuilder() {
        return Order.builder()
                .memberId(DEFAULT_MEMBER_ID)
                .request(DEFAULT_REQUEST)
                .recipient(DEFAULT_RECIPIENT)
                .products(List.of(ProductFixture.createDefaultProduct()))
                .status(DEFAULT_STATUS);
    }

    public static Order createDefaultOrder() {
        return createDefaultOrderBuilder()
                .build();
    }

    public static Order createDefaultOrderWithId(Long id) {
        Order order = createDefaultOrder();
        ReflectionTestUtils.setField(order, "id", id);
        return order;
    }

    public static OrderResponse createOrderResponse(Order order) {
        return OrderResponse.from(order);
    }

    private static OrderRequest.OrderRequestBuilder createDefaultOrderRequest() {
        return OrderRequest.builder()
                .request(DEFAULT_REQUEST)
                .cartItems(List.of(CartItemFixture.createCartItemRequestWithQuantity(ProductFixture.DEFAULT_QUANTITY)))
                .recipient(RecipientFixture.createDefaultRecipientRequest())
                .orderCouponIds(List.of(OrderCouponFixture.DEFAULT_COUPON_ID));
    }

    public static OrderRequest createOrderRequestWithCartItems(List<CartItemRequest> cartItemRequests) {
        return createDefaultOrderRequest()
                .cartItems(cartItemRequests)
                .build();
    }

    public static OrderRequest createOrderRequestWithOrderCouponIds(List<Long> orderCouponIds) {
        return createDefaultOrderRequest()
                .orderCouponIds(orderCouponIds)
                .build();
    }

    public static OrderRequest createOrderRequestWithRecipient(RecipientRequest recipient) {
        return createDefaultOrderRequest()
                .recipient(recipient)
                .build();
    }

    public static OrderRequest createOrderRequestWithRequest(String request) {
        return createDefaultOrderRequest()
                .request(request)
                .build();
    }
}
