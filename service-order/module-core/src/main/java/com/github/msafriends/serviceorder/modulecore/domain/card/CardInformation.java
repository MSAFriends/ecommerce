package com.github.msafriends.serviceorder.modulecore.domain.card;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

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
        validateCardInformation(cardNumber, expiration, cvv);

        this.cardNumber = cardNumber;
        this.expiration = expiration;
        this.cvv = cvv;
    }

    private void validateCardInformation(String cardNumber, CardExpiration expiration, String cvv) {
        validateNotNull(cardNumber, expiration, cvv);
        validateCardNumber(cardNumber);
        validateCvv(cvv);
    }

    private void validateNotNull(String cardNumber, CardExpiration expiration, String cvv) {
        Assert.notNull(cardNumber, "cardNumber must not be null");
        Assert.notNull(expiration, "expiration must not be null");
        Assert.notNull(cvv, "cvv must not be null");
    }

    private void validateCardNumber(String cardNumber) {
        Assert.isTrue(cardNumber.length() == 16, "cardNumber must be 16 digits");
    }

    private void validateCvv(String cvv) {
        Assert.isTrue(cvv.length() == 3, "cvv must be 3 digits");
    }
}
