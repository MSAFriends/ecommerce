package com.github.msafriends.serviceorder.modulecore.domain.order;

import com.github.msafriends.serviceorder.modulecore.base.BaseTimeEntity;
import com.github.msafriends.serviceorder.modulecore.dto.CouponResponse;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private Integer totalPrice;

    @Column(nullable = false)
    private Integer discountedPrice;

    @Embedded
    private Recipient recipient;

    private String request;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OrderItem> orderItems = new ArrayList<>();

    public Order(Long memberId, String request, Recipient recipient, OrderStatus status, List<CouponResponse> coupons) {
        validateOrder();

        this.memberId = memberId;
        this.request = request;
        this.recipient = recipient;
        this.status = status;
    }

    private void validateOrder() {
        validateNotNull();
    }

    private void validateNotNull() {
        Assert.notNull(status, "status must not be null");
        Assert.notNull(memberId, "memberId must not be null");
        Assert.notNull(totalPrice, "totalPrice must not be null");
        Assert.notNull(recipient, "recipient must not be null");
    }

    private int calculateTotalPrice() {
        return orderItems.stream()
                .mapToInt(item -> item.getProduct().getPrice() * item.getProduct().getQuantity())
                .sum();
    }
}
