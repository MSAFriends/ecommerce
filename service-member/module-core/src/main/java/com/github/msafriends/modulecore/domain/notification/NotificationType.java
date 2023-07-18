package com.github.msafriends.modulecore.domain.notification;

import lombok.Getter;

@Getter
public enum NotificationType {
    ORDER_REJECTED("ORDER_REJECTED", "{sellerName}님이 주문을 거절하셨습니다."),
    ORDER_COMPLETED("ORDER_COMPLETED", "{sellerName}님으로 부터 주문접수가 완료하셨습니다."),
    COUPON_DISTRIBUTION("COUPON_DISTRIBUTION", "{couponName} 쿠폰이 발급되었습니다."),
    MANAGER_NEW_EVENT("MANAGER_NEW_EVENT", "{managerName}님의 새로운 이벤트가 등록되었습니다.");

    private final String code;
    private final String messageTemplate;

    NotificationType(String code, String messageTemplate) {
        this.code = code;
        this.messageTemplate = messageTemplate;
    }
}