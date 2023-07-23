package com.github.msafriends.modulecore.domain.coupon;

import com.github.msafriends.modulecore.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member_coupons")
public class MemberCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_coupon_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @Column(nullable = false)
    private Boolean hasUsed = false;

    @Column(nullable = false)
    private LocalDateTime usedAt;

    @Column(nullable = false)
    private LocalDateTime startAt;

    @Column(nullable = false)
    private LocalDateTime endAt;

    @Builder
    public MemberCoupon(Member member, Coupon coupon, LocalDateTime usedAt, LocalDateTime startAt, LocalDateTime endAt) {
        validateCouponExpirationDateCorrectness(startAt, endAt);
        this.member = member;
        this.coupon = coupon;
        this.hasUsed = false;
        this.usedAt = usedAt;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public void use(LocalDateTime currentTime) {
        checkCouponValidity(currentTime);
        if (this.hasUsed) {
            throw new IllegalStateException("The coupon has already been used.");
        }
        this.hasUsed = true;
    }

    private void checkCouponValidity(LocalDateTime currentTime) {
        if (currentTime.isBefore(startAt) || currentTime.isAfter(endAt)) {
            throw new IllegalStateException("The coupon is not within its validity period.");
        }
    }

    private void validateCouponExpirationDateCorrectness(LocalDateTime startAt, LocalDateTime endAt) {
        if (endAt.isBefore(startAt)) {
            throw new IllegalStateException("The coupon's expiration date is incorrectly set.");
        }
    }
}
