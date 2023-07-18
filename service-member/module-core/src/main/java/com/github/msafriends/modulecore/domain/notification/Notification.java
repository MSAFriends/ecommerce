package com.github.msafriends.modulecore.domain.notification;

import com.github.msafriends.modulecore.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.Assert;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "notifications")
public class Notification {

    public static String MANAGER_NAME = "manager";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private Boolean isRead = false;

    @Column(nullable = false)
    private String senderName;

    @Column(nullable = false)
    private String pageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationSenderType senderType;

    @Builder(builderClassName = "ByOrderEventBuilder", builderMethodName = "ByOrderEventBuilder")
    public Notification(Member member, String senderName, String pageUrl, NotificationType type) {
        this.member = member;
        this.senderName = senderName;
        this.pageUrl = pageUrl;
        this.type = type;
        this.senderType = NotificationSenderType.SELLER;
        this.isRead = false;

        validateSenderName(senderName);
    }

    @Builder(builderClassName = "ByManagerEventBuilder", builderMethodName = "ByManagerEventBuilder")
    public Notification(String pageUrl, NotificationType type) {
        this.pageUrl = pageUrl;
        this.type = type;
        this.senderName = MANAGER_NAME;
        this.senderType = NotificationSenderType.ADMIN;
    }

    private void validateSenderName(String senderName) {
        Assert.hasText(senderName, "senderName must not be empty");
    }

}
