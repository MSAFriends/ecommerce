package com.github.msafriends.modulecore.domain.grade;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "grades")
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_id")
    private Long id;

    @OneToMany(mappedBy = "grade", cascade = CascadeType.ALL)
    private List<GradeBenefit> benefits = new ArrayList<>();

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private int minOrderCountForGrade;

    @Column(unique = true)
    private int maxOrderCountForGrade;

    @Builder
    public Grade (String name, int minOrderCountForGrade, int maxOrderCountForGrade) {
        validateOrderCountsForGrade(minOrderCountForGrade, maxOrderCountForGrade);
        Assert.hasText(name, "Grade name must not be null or empty.");
        this.name = name;
        this.minOrderCountForGrade = minOrderCountForGrade;
        this.maxOrderCountForGrade = maxOrderCountForGrade;
    }

    private void validateOrderCountsForGrade(int minOrderCount, int maxOrderCount) {
        if (minOrderCount > maxOrderCount) {
            throw new IllegalArgumentException("Invalid order counts: minOrderCount must be less than or equal to maxOrderCount.");
        }
    }

    public void addBenefit(GradeBenefit benefits) {
        this.benefits.add(benefits);
    }
}
