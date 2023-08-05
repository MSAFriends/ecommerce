package com.github.msafriends.serviceorder.modulecore.domain.order;

import com.github.msafriends.modulecommon.base.BaseTimeEntity;
import com.github.msafriends.serviceorder.modulecore.domain.product.OrderProduct;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Entity
@Getter
@Table(name = "cart_items", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"order_id", "product_id"})})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItem extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Embedded
    private OrderProduct product;

    @Builder
    public CartItem(Order order, OrderProduct product) {
        validateCartItem(order, product);

        this.order = order;
        this.product = product;
    }

    private void validateCartItem(Order order, OrderProduct product) {
        validateNotNull(order, product);
    }

    private void validateNotNull(Order order, OrderProduct product) {
        Assert.notNull(order, "order must not be null");
        Assert.notNull(product, "product must not be null");
    }
}
