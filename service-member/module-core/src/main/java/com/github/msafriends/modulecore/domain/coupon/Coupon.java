package com.github.msafriends.modulecore.domain.coupon;

import com.github.msafriends.modulecore.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "coupons")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String uuid;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CouponDiscountType discountType;

    @Column(nullable = false)
    private int value;

    @Column(nullable = false)
    private Boolean hasUsed = false;

    @Column(nullable = false)
    private LocalDateTime startAt;

    @Column(nullable = false)
    private LocalDateTime endAt;


    @Builder
    public Coupon (Member member, CouponDiscountType discountType, int value, LocalDateTime startAt, LocalDateTime endAt) {
        validateValue(value);
        this.member = member;
        this.uuid = generateUuid();
        this.discountType = discountType;
        this.value = value;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public void use() {
        checkCouponValidity();
        if (!this.hasUsed) {
            this.hasUsed = true;
        }
    }

    private void checkCouponValidity() {
        LocalDateTime currentDate = LocalDateTime.now();

        if (currentDate.isBefore(startAt) || currentDate.isAfter(endAt)) {
            throw new IllegalStateException("The coupon is not within its validity period.");
        }
    }

    private void validateValue (int value) {
        if (value <= 0) {
            throw new IllegalArgumentException("Value must be greater than 0.");
        }

        if (discountType.equals(CouponDiscountType.PERCENTAGE) && value >100) {
            throw new IllegalArgumentException("Percentage value cannot exceed 100.");
        }
    }

    private String generateUuid() {
        return "";
    }
}
