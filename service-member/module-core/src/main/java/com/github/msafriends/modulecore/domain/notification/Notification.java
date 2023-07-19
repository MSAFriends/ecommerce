package com.github.msafriends.modulecore.domain.notification;

import com.github.msafriends.modulecore.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.Assert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Column(nullable = false)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationSenderType senderType;

    @Builder(builderClassName = "ByOrderEventBuilder", builderMethodName = "ByOrderEventBuilder")
    public Notification(Member member, String senderName, String pageUrl, NotificationType type) {
        validateSenderName(senderName);

        this.member = member;
        this.senderName = senderName;
        this.pageUrl = pageUrl;
        this.message = generateMessage(senderName, type);
        this.type = type;
        this.senderType = NotificationSenderType.SELLER;
        this.isRead = false;
    }

    @Builder(builderClassName = "ByManagerEventBuilder", builderMethodName = "ByManagerEventBuilder")
    public Notification(Member member, String pageUrl, NotificationType type) {
        this.member = member;
        this.senderName = MANAGER_NAME;
        this.pageUrl = pageUrl;
        this.message = generateMessage(senderName, type);
        this.type = type;
        this.senderType = NotificationSenderType.ADMIN;
        this.isRead = false;
    }

    private String generateMessage(String senderName, NotificationType type) {
        String message = type.getMessageTemplate();
        Pattern pattern = Pattern.compile("\\{(.+?)\\}");
        Matcher matcher = pattern.matcher(message);
        return matcher.replaceAll(senderName);
    }

    private void validateSenderName(String senderName) {
        Assert.hasText(senderName, "senderName must not be empty");
    }

}
