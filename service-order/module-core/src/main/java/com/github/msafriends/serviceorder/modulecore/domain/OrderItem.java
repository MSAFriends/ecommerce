package com.github.msafriends.serviceorder.modulecore.domain;

import com.github.msafreinds.serviceorder.modulecore.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Entity
@Getter
@Table(name = "order_items")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Embedded
    private Product product;

    @Builder
    public OrderItem(Order order, Product product) {
        validateOrderItem();

        this.order = order;
        this.product = product;
    }

    private void validateOrderItem() {
        validateNotNull();
    }

    private void validateNotNull() {
        Assert.notNull(order, "order must not be null");
        Assert.notNull(order, "product must not be null");
    }
}
