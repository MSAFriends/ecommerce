package com.github.msafriends.modulecore.domain.grade;

import com.github.msafriends.modulecore.domain.coupon.Coupon;
import com.github.msafriends.modulecore.domain.coupon.CouponDiscountType;
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
    private String name;

    @Column(nullable = false)
    private CouponDiscountType discountType;

    @Column(nullable = false)
    private int value;

    @Builder
    public GradeBenefit (Grade grade, String name, CouponDiscountType discountType, int value) {
        validateValue(value);
        Assert.hasText(name, "Name must not be empty.");

        this.grade = grade;
        this.name = name;
        this.discountType = discountType;
        this.value = value;
    }

    private void validateValue (int value) {
        if (value <= 0) {
            throw new IllegalArgumentException("Value must be greater than 0.");
        }

        if (discountType.equals(CouponDiscountType.PERCENTAGE) && value >100) {
            throw new IllegalArgumentException("Percentage value cannot exceed 100.");
        }
    }
}
