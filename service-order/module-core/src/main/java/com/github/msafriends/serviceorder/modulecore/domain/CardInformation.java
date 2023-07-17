package com.github.msafriends.serviceorder.modulecore.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardInformation {

    @Column(nullable = false)
    private String cardNumber;

    @Embedded
    private CardExpiration expiration;

    @Column(nullable = false)
    private String cvv;

    @Builder
    public CardInformation(String cardNumber, CardExpiration expiration, String cvv) {
        this.cardNumber = cardNumber;
        this.expiration = expiration;
        this.cvv = cvv;
    }
}
