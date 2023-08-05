package com.github.msafriends.modulecore.domain.order;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "order_confirm")
public class OrderConfirm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_confirm_id")
    private Long id;

    @Column(nullable = false)
    private Long orderId;

    private OrderConfirmStatus status;

    private LocalDateTime confirmedAt;

    @Builder
    public OrderConfirm(Long orderId) {
        this.orderId = orderId;
        this.status = OrderConfirmStatus.PENDING;
    }

    public void reject(LocalDateTime now) {
        this.status = OrderConfirmStatus.REJECT;
        this.confirmedAt = now;
    }

    public void accept(LocalDateTime now) {
        this.status = OrderConfirmStatus.ACCEPT;
        this.confirmedAt = now;
    }
}
