package com.github.msafriends.serviceorder.modulecore.domain.card;

import com.github.msafriends.serviceorder.modulecore.domain.bank.BankProvider;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Entity
@Getter
@Table(name = "card_payments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_payment_id")
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Embedded
    private CardInformation cardInformation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_provider_id")
    private BankProvider bankProvider;

    @Column(nullable = false)
    private String passwordPartial;

    @Builder
    public CardPayment(Long memberId, CardInformation cardInformation, BankProvider bankProvider, String passwordPartial) {
        validateCardPayment(memberId, cardInformation, bankProvider, passwordPartial);

        this.memberId = memberId;
        this.cardInformation = cardInformation;
        this.bankProvider = bankProvider;
        this.passwordPartial = passwordPartial;
    }

    private void validateCardPayment(Long memberId, CardInformation cardInformation, BankProvider bankProvider, String passwordPartial) {
        validateNotNull(memberId, cardInformation, bankProvider, passwordPartial);
        validatePasswordPartial(passwordPartial);
    }

    private void validatePasswordPartial(String passwordPartial) {
        Assert.isTrue(passwordPartial.length() == 2, "passwordPartial must be two digits");
    }

    private void validateNotNull(Long memberId, CardInformation cardInformation, BankProvider bankProvider, String passwordPartial) {
        Assert.notNull(memberId, "memberId must not be null");
        Assert.notNull(cardInformation, "cardInformation must not be null");
        Assert.notNull(bankProvider, "bankProvider must not be null");
        Assert.notNull(passwordPartial, "passwordPartial must not be null");
    }
}
