package com.github.msafriends.serviceorder.modulecore.domain.card;

import com.github.msafriends.serviceorder.modulecore.domain.bank.BankProvider;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_provider_id")
    private BankProvider bankProvider;

    @Column(nullable = false)
    private String passwordPartial;

    @Builder
    public CardPayment(Long memberId, CardInformation cardInformation, BankProvider bankProvider, String passwordPartial) {
        this.memberId = memberId;
        this.cardInformation = cardInformation;
        this.bankProvider = bankProvider;
        this.passwordPartial = passwordPartial;
    }
}
