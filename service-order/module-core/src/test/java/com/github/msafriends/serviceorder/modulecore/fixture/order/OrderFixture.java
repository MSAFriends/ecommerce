package com.github.msafriends.serviceorder.modulecore.fixture.order;

import com.github.msafriends.serviceorder.modulecore.domain.order.Order;
import com.github.msafriends.serviceorder.modulecore.domain.order.OrderStatus;
import com.github.msafriends.serviceorder.modulecore.domain.order.Recipient;

public class OrderFixture {
    private static final Long DEFAULT_MEMBER_ID = 1L;
    private static final String DEFAULT_REQUEST = "테스트 요청사항";
    private static final Recipient DEFAULT_RECIPIENT = RecipientFixture.createDefaultRecipient();
    private static final OrderStatus DEFAULT_STATUS = OrderStatus.APPROVAL_PENDING;

    private static Order.OrderBuilder createDefaultOrderBuilder() {
        return Order.builder()
                .memberId(DEFAULT_MEMBER_ID)
                .request(DEFAULT_REQUEST)
                .recipient(DEFAULT_RECIPIENT)
                .status(DEFAULT_STATUS);
    }

    public static Order createDefaultOrder() {
        return createDefaultOrderBuilder()
                .build();
    }
}
