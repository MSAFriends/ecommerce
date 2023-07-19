package com.github.msafriends.modulecore.domain.notification;

import com.github.msafriends.modulecore.domain.coupon.Coupon;
import com.github.msafriends.modulecore.domain.member.Member;
import com.github.msafriends.modulecore.domain.member.Seller;
import com.github.msafriends.modulecore.fixture.CouponFixture;
import com.github.msafriends.modulecore.fixture.MemberFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class NotificationTest {

    private static final String REDIRECT_URL = "https://example.com";

    @Nested
    @DisplayName("알림 메시지 테스트")
    class NotificationMessageTemplateTests {

        @Test
        @DisplayName("주문 관련 알림 생성이 성공한다.")
        void testNotificationMessageWithOrder() {
            Member member = MemberFixture.createMember();
            Seller seller = MemberFixture.createSeller();
            String messageOrderCompleted = NotificationType.ORDER_COMPLETED.getMessageTemplate();
            String messageOrderRejected = NotificationType.ORDER_REJECTED.getMessageTemplate();

            Notification notificationOrderCompleted = Notification.ByOrderEventBuilder()
                    .member(member)
                    .pageUrl(REDIRECT_URL)
                    .type(NotificationType.ORDER_COMPLETED)
                    .senderName(seller.getNickName())
                    .build();
            assertThat(notificationOrderCompleted.getMessage()).isEqualTo(messageOrderCompleted.replace("{sellerName}", seller.getNickName()));

            Notification notificationOrderRejected = Notification.ByOrderEventBuilder()
                    .member(member)
                    .pageUrl(REDIRECT_URL)
                    .type(NotificationType.ORDER_REJECTED)
                    .senderName(seller.getNickName())
                    .build();
            assertThat(notificationOrderRejected.getMessage()).isEqualTo(messageOrderRejected.replace("{sellerName}", seller.getNickName()));
        }

        @Test
        @DisplayName("쿠폰 관련 알림 생성이 성공한다.")
        void testNotificationMessageWithCoupon() {
            Member member = MemberFixture.createMember();
            Coupon coupon = CouponFixture.createCoupon(member);
            String message = NotificationType.COUPON_DISTRIBUTION.getMessageTemplate();

            Notification notificationWithCoupon = Notification.ByOrderEventBuilder()
                    .member(member)
                    .pageUrl(REDIRECT_URL)
                    .type(NotificationType.COUPON_DISTRIBUTION)
                    .senderName(coupon.getName())
                    .build();
            assertThat(notificationWithCoupon.getMessage()).isEqualTo(message.replace("{couponName}", coupon.getName()));
        }

        @Test
        @DisplayName("관리자 관련 알림 생성이 성공한다.")
        void testNotificationMessageWithManager() {
            Member member = MemberFixture.createMember();
            String managerName = "manager";
            String message = NotificationType.MANAGER_NEW_EVENT.getMessageTemplate();

            Notification notification = Notification.ByManagerEventBuilder()
                    .member(member)
                    .pageUrl(REDIRECT_URL)
                    .type(NotificationType.MANAGER_NEW_EVENT)
                    .build();
            assertThat(notification.getMessage()).isEqualTo(message.replace("{managerName}", managerName));
        }

        @Test
        @DisplayName("알림 생성 시 senderName이 없을 경우 실패한다.")
        void testNotificationMessageWithBlankSenderName() {
            Member member = MemberFixture.createMember();

            assertThatThrownBy(() ->
                    Notification.ByOrderEventBuilder()
                            .member(member)
                            .pageUrl(REDIRECT_URL)
                            .type(NotificationType.COUPON_DISTRIBUTION)
                            .senderName("")
                            .build())
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("senderName must not be empty");
        }
    }
}