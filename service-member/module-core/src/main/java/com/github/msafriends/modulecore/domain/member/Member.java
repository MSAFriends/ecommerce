package com.github.msafriends.modulecore.domain.member;

import com.github.msafriends.modulecore.domain.coupon.Coupon;
import com.github.msafriends.modulecore.domain.grade.MemberGrade;
import com.github.msafriends.modulecore.domain.notification.Notification;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberGrade> memberGrades = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Coupon> coupons = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<FavoriteSellerSubscription> favoriteSellerSubscriptions = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Notification> notifications = new ArrayList<>();

    @Embedded
    private Email email;

    @Column(nullable = false, length=20)
    private String password;

    @Column(nullable = false, length=13)
    private String phoneNumber;

    @Column(nullable = false, length=20)
    private String name;

    private String currentAddress;

    @Builder
    public Member(String email, String password, String phoneNumber, String name) {
        this.email = new Email(email);
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.name = name;

        validatePhoneNumber(phoneNumber);
    }

    public void updateCurrentAddress(String address) {
        Assert.hasText(address, "Address must not be null or empty.");
        this.currentAddress = address;
    }

    public void updatePhoneNumber(String phoneNumber) {
        validatePhoneNumber(phoneNumber);
        this.phoneNumber = phoneNumber;
    }

    public void updateEmail(String email) {
        this.email = new Email(email);
    }

    private void validatePhoneNumber(String phoneNumber) {
        Assert.hasText(phoneNumber, "Phone number must not be null or empty.");
        if (!Pattern.matches("^\\d{2,3}-\\d{3,4}-\\d{4}$", phoneNumber)) {
            throw new IllegalArgumentException("Invalid phone number format.");
        }
    }
}
