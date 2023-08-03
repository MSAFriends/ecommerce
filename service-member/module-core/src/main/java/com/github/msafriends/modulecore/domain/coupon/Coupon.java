package com.github.msafriends.modulecore.domain.coupon;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "coupons")
public class Coupon {

    private static final int DEFAULT_MAX_QUANTITY_MEMBER = 1;
    private static final int DEFAULT_QUANTITY = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long id;

    @OneToMany(mappedBy = "coupon", cascade = CascadeType.ALL)
    private List<MemberCoupon> memberCoupons = new ArrayList<>();

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CouponDiscountType discountType;

    @Column(nullable = false)
    private int value;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CouponGenerateType generateType;

    private int quantity;

    @Column(nullable = false)
    private int validationRange;

    @Column(nullable = false)
    private Integer maxQuantityPerMember;

    @Builder(builderClassName = "ByLimitedBuilder", builderMethodName = "ByLimitedBuilder")
    public Coupon(String name, CouponDiscountType discountType, int value, CouponGenerateType generateType, int quantity, int validationRange, Integer maxQuantityPerMember) {
        validateValue(value, discountType);
        this.name = name;
        this.discountType = discountType;
        this.value = value;
        this.generateType = generateType;
        this.quantity = quantity;
        this.validationRange = validationRange;
        this.maxQuantityPerMember = generateMaxQuantityPerMember(maxQuantityPerMember);
    }

    @Builder(builderClassName = "ByUnLimitedBuilder", builderMethodName = "ByUnLimitedBuilder")
    public Coupon(String name, CouponDiscountType discountType, int value, CouponGenerateType generateType, int validationRange, Integer maxQuantityPerMember) {
        validateValue(value, discountType);
        this.name = name;
        this.discountType = discountType;
        this.value = value;
        this.generateType = generateType;
        this.quantity = DEFAULT_QUANTITY;
        this.validationRange = validationRange;
        this.maxQuantityPerMember = generateMaxQuantityPerMember(maxQuantityPerMember);
    }

    public void addMemberCoupon(MemberCoupon memberCoupon) {
        this.memberCoupons.add(memberCoupon);
    }

    public void reduceQuantity(int quantity) {
        this.quantity = this.quantity - quantity;
    }

    private int generateMaxQuantityPerMember(Integer maxQuantityPerMember) {
        if (maxQuantityPerMember == null) {
            return DEFAULT_MAX_QUANTITY_MEMBER;
        }
        return maxQuantityPerMember;
    }

    private void validateValue (int value, CouponDiscountType discountType) {
        if (value <= 0) {
            throw new IllegalArgumentException("Value must be greater than 0.");
        }

        if (discountType.equals(CouponDiscountType.PERCENTAGE) && value >100) {
            throw new IllegalArgumentException("Percentage value cannot exceed 100.");
        }
    }
}
