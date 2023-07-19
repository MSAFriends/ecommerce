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
    private String cvc;

    @Builder
    public CardInformation(String cardNumber, CardExpiration expiration, String cvc) {
        validateCardInformation(cardNumber, expiration, cvc);

        this.cardNumber = cardNumber;
        this.expiration = expiration;
        this.cvc = cvc;
    }

    private void validateCardInformation(String cardNumber, CardExpiration expiration, String cvc) {
        validateNotNull(cardNumber, expiration, cvc);
        validateCardNumber(cardNumber);
        validateCvc(cvc);
    }

    private void validateNotNull(String cardNumber, CardExpiration expiration, String cvc) {
        Assert.notNull(cardNumber, "cardNumber must not be null");
        Assert.notNull(expiration, "expiration must not be null");
        Assert.notNull(cvc, "cvc must not be null");
    }

    private void validateCardNumber(String cardNumber) {
        Assert.isTrue(cardNumber.length() == 16, "cardNumber must be 16 digits");
    }

    private void validateCvc(String cvc) {
        Assert.isTrue(cvc.length() == 3, "cvc must be 3 digits");
    }
}
