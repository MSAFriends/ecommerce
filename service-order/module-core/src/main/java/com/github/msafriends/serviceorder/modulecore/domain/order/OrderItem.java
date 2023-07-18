package com.github.msafriends.serviceorder.modulecore.domain.order;

import com.github.msafriends.serviceorder.modulecore.base.BaseTimeEntity;
import com.github.msafriends.serviceorder.modulecore.domain.product.Product;
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
        validateOrderItem(order, product);

        this.order = order;
        this.product = product;
    }

    private void validateOrderItem(Order order, Product product) {
        validateNotNull(order, product);
    }

    private void validateNotNull(Order order, Product product) {
        Assert.notNull(order, "order must not be null");
        Assert.notNull(product, "product must not be null");
    }
}
