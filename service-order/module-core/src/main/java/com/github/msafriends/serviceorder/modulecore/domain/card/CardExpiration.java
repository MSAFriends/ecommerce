package com.github.msafriends.serviceorder.modulecore.domain.card;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.time.LocalDate;

@Getter
@Embeddable
@NoArgsConstructor
public class CardExpiration {

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private Integer month;

    @Builder
    public CardExpiration(Integer year, Integer month) {
        validateCardExpiration(year, month);

        this.year = year;
        this.month = month;
    }

    private void validateCardExpiration(Integer year, Integer month) {
        validateNotNull(year, month);
        validateRange(year, month);
    }

    private void validateNotNull(Integer year, Integer month) {
        Assert.notNull(year, "year must not be null");
        Assert.notNull(month, "month must not be null");
    }

    private void validateRange(Integer year, Integer month) {
        Assert.isTrue(year >= 0, "year must be greater than or equal to 0");
        Assert.isTrue(month >= 1 && month <= 12, "month must be between 1 and 12");
    }

    public boolean isExpired(LocalDate currentDate) {
        LocalDate expiryDate = LocalDate.of(this.year, this.month, 1);
        return currentDate.isAfter(expiryDate);
    }
}
