package com.github.msafriends.modulecore.domain.grade;

import com.github.msafriends.modulecore.domain.coupon.Coupon;
import com.github.msafriends.modulecore.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member_grades")
public class MemberGrade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_grade_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grade_id")
    private Grade grade;

    @Column(nullable = false)
    private Boolean isIssued = false;

    @Builder
    public MemberGrade(Member member, Grade grade) {
        this.member = member;
        this.grade = grade;
        this.isIssued = false;
    }

    public List<GradeBenefit> getGradeBenefit() {
        return this.grade.getBenefits();
    }

    public List<Coupon> generateGradeBenefitCoupons() {
        List<GradeBenefit> gradeBenefits = this.grade.getBenefits();

        return gradeBenefits.stream()
                .map(gradeBenefit -> Coupon.builder()
                        .member(member)
                        .discountType(gradeBenefit.getDiscountType())
                        .value(gradeBenefit.getValue())
                        .name(gradeBenefit.getName())
                        .startAt(generateCouponStartAt())
                        .endAt(generateCouponEndAt())
                        .build())
                .collect(Collectors.toList());
    }

    private LocalDateTime generateCouponStartAt() {
        return LocalDateTime.now()
                .withDayOfMonth(1)
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(1);
    }

    private LocalDateTime generateCouponEndAt() {
        return LocalDateTime.now()
                .withDayOfMonth(LocalDate.now().lengthOfMonth())
                .withHour(23)
                .withMinute(59)
                .withSecond(59)
                .withNano(0);
    }
}
