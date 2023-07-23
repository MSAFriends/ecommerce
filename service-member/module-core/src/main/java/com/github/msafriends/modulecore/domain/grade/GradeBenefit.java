package com.github.msafriends.modulecore.domain.grade;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.Assert;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "grade_benefits")
public class GradeBenefit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="grade_benefit_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grade_id")
    private Grade grade;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BenefitType benefitType;
    private int value;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Builder
    public GradeBenefit (Grade grade, BenefitType benefitType, int value, String name, String description) {
        Assert.hasText(name, "Name must not be empty.");
        validateValue(value, benefitType);
        this.benefitType = benefitType;
        this.value = value;
        this.grade = grade;
        this.name = name;
        this.description = description;
    }

    public boolean isDiscountDeliveryBenefit() {
        return benefitType == BenefitType.DELIVERY_DISCOUNT;
    }

    public boolean isFixedDiscountCouponBenefit() {
        return benefitType == BenefitType.FIXED_DISCOUNT_COUPON;
    }

    public boolean isPercentageDiscountCouponBenefit() {
        return benefitType == BenefitType.PERCENTAGE_DISCOUNT_COUPON;
    }

    private void validateValue (int value, BenefitType benefitType) {
        if (value <= 0) {
            throw new IllegalArgumentException("Value must be greater than 0.");
        }

        if (benefitType.equals(BenefitType.PERCENTAGE_DISCOUNT_COUPON) && value >100) {
            throw new IllegalArgumentException("Percentage value cannot exceed 100.");
        }
    }
}
