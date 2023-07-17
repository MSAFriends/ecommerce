package com.github.msafriends.serviceorder.modulecore.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class CardExpiration {

    @Column(nullable = false)
    private String year;

    @Column(nullable = false)
    private String month;

    @Builder
    public CardExpiration(String year, String month) {
        this.year = year;
        this.month = month;
    }
}
