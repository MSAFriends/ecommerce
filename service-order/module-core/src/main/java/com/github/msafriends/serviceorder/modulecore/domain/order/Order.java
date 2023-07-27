package com.github.msafriends.serviceorder.modulecore.domain.order;

import com.github.msafriends.modulecommon.base.BaseTimeEntity;
import com.github.msafriends.modulecommon.exception.ErrorCode;
import com.github.msafriends.modulecommon.exception.InvalidValueException;
import com.github.msafriends.serviceorder.modulecore.domain.coupon.OrderCoupon;
import com.github.msafriends.serviceorder.modulecore.domain.coupon.strategy.PriceCalculator;
import com.github.msafriends.serviceorder.modulecore.dto.request.order.UpdateCartItemRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    private Long sellerId;

    @Embedded
    private Recipient recipient;

    private String request;

    @Column(nullable = false)
    private Integer totalPrice;

    @Column(nullable = false)
    private Integer discountedPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderCoupon> coupons = new ArrayList<>();

    @Builder(builderClassName = "PendingOrderBuilder", builderMethodName = "createPendingOrderBuilder")
    public Order(Long memberId) {
        validateOrder(memberId);

        this.memberId = memberId;
        this.status = OrderStatus.PENDING;
    }

    public void addCoupon(OrderCoupon coupon) {
        coupons.add(coupon);
        recalculatePrice();
    }

    public void addCoupons(List<OrderCoupon> coupons) {
        this.coupons.addAll(coupons);
        recalculatePrice();
    }

    public void updateCartItem(UpdateCartItemRequest request) {
        validateOrderIsPending();
        validateSellerId(request.getSellerId());

        Optional<CartItem> existingCartItem = findExistingCartItemByProductId(request.getProductId());
        if (existingCartItem.isPresent()) {
            updateExistingCartItem(existingCartItem.get(), request);
        } else {
            this.sellerId = request.getSellerId();
            cartItems.add(UpdateCartItemRequest.toCartItem(this, request));
        }

        recalculatePrice();
    }

    private void updateExistingCartItem(CartItem existingCartItem, UpdateCartItemRequest request) {
        int totalQuantity = existingCartItem.getProduct().getQuantity() + request.getQuantity();
        if (totalQuantity == 0) {
            cartItems.remove(existingCartItem);
            if (cartItems.isEmpty()) {
                this.sellerId = null;
            }
        } else {
            existingCartItem.getProduct().addQuantity(request.getQuantity());
        }
    }

    private Optional<CartItem> findExistingCartItemByProductId(Long productId) {
        return getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findAny();
    }

    private void recalculatePrice() {
        var priceCalculator = new PriceCalculator(cartItems, coupons);
        this.totalPrice = priceCalculator.calculateTotalPrice();
        this.discountedPrice = priceCalculator.calculateDiscountedPrice();
    }

    private void validateOrder(Long memberId) {
        validateNotNull(memberId);
    }

    public void validateSellerId(Long sellerId) {
        if (sellerId == null) {
            throw new InvalidValueException(ErrorCode.INVALID_INPUT_VALUE, "판매자는 필수");
        }

        if (this.sellerId != null && !sellerId.equals(this.sellerId)) {
            throw new InvalidValueException(ErrorCode.INVALID_INPUT_VALUE, "판매자는 동일해야 함");
        }
    }

    private void validateOrderIsPending() {
        // TODO: 향후 CustomException으로 전환하기
        if (!OrderStatus.PENDING.equals(status)) {
            throw new RuntimeException("Order is not pending");
        }
    }

    private void validateNotNull(Long memberId) {
        Assert.notNull(memberId, "memberId must not be null");
    }
}
