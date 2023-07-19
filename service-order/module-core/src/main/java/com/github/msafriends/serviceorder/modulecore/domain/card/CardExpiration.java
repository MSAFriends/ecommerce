package com.github.msafriends.serviceorder.modulecore.domain.card;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.YearMonth;

@Getter
@Embeddable
@NoArgsConstructor
public class CardExpiration {

    @Column(nullable = false, updatable = false)
    private Integer year;

    @Column(nullable = false, updatable = false)
    private Integer month;

    @Builder
    public CardExpiration(Integer year, Integer month) {
        validateCardExpiration(year, month);

        this.year = adjustToCurrentCentury(year);
        this.month = month;
    }

    private int adjustToCurrentCentury(Integer twoDigitYear) {
        int currentCentury = LocalDate.now().getYear() / 100 * 100;
        return currentCentury + twoDigitYear;
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
        Assert.isTrue(year >= 0 && year <= 99, "year must be between 0 and 99");
        Assert.isTrue(month >= 1 && month <= 12, "month must be between 1 and 12");
    }

    public boolean isExpired(LocalDate currentDate) {
        LocalDate expiryDate = YearMonth.of(year, month).atEndOfMonth();
        return currentDate.isAfter(expiryDate);
    }
}
