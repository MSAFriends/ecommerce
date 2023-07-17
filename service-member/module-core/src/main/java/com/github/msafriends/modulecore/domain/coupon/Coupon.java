package com.github.msafriends.modulecore.domain.coupon;

import com.github.msafriends.modulecore.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.Assert;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
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
    private Boolean hasUsed;

    @Builder
    public Coupon (Member member, CouponDiscountType discountType, int value) {
        validateValue(value);
        this.member = member;
        this.uuid = generateUuid();
        this.discountType = discountType;
        this.value = value;
        this.hasUsed = false;
    }

    public void use() {
        if (!this.hasUsed) {
            this.hasUsed = true;
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
