package com.github.msafriends.serviceorder.modulecore.domain.order;

import com.github.msafriends.serviceorder.modulecore.dto.request.order.UpdateCartItemRequest;
import com.github.msafriends.serviceorder.modulecore.fixture.CartItemFixture;
import com.github.msafriends.serviceorder.modulecore.fixture.OrderFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class OrderTest {

    @Nested
    @DisplayName("주문 항목의 수량 변경")
    class UpdateCartItemTest {
        @Test
        @DisplayName("주문이 PENDING 상태일 때, 주문 항목의 수량을 변경할 수 있다.")
        void testUpdateCartItem() {
            // given
            int quantity = 5;
            Order pendingOrder = OrderFixture.createDefaultPendingOrder();

            // when
            pendingOrder.updateCartItem(CartItemFixture.createUpdateCartItemRequestWithQuantity(quantity));

            // then
            assertThat(pendingOrder.getCartItems().get(0).getProduct().getQuantity()).isEqualTo(quantity);
        }

        @Test
        @DisplayName("같은 상품을 여러 번 주문했을 때, 주문 항목의 수량이 누적된다.")
        void testUpdateCartItemWithSameProduct() {
            // given
            int quantity = 5;
            Order pendingOrder = OrderFixture.createDefaultPendingOrder();

            // when
            pendingOrder.updateCartItem(CartItemFixture.createUpdateCartItemRequestWithQuantity(quantity));
            pendingOrder.updateCartItem(CartItemFixture.createUpdateCartItemRequestWithQuantity(quantity));

            // then
            assertThat(pendingOrder.getCartItems().get(0).getProduct().getQuantity()).isEqualTo(quantity * 2);
        }

        @Test
        @DisplayName("주문 항목의 수량이 0이 되면, 주문 항목이 삭제된다.")
        void testUpdateCartItemWithZeroQuantity() {
            // given
            int quantity = 5;
            Order pendingOrder = OrderFixture.createDefaultPendingOrder();

            // when
            pendingOrder.updateCartItem(CartItemFixture.createUpdateCartItemRequestWithQuantity(quantity));
            pendingOrder.updateCartItem(CartItemFixture.createUpdateCartItemRequestWithQuantity(-quantity));

            // then
            assertThat(pendingOrder.getCartItems()).isEmpty();
        }

        @Test
        @DisplayName("주문 항목의 수량이 음수가 되면, 예외가 발생한다.")
        void testUpdateCartItemWithNegativeQuantity() {
            // given
            int quantity = -5;
            Order pendingOrder = OrderFixture.createDefaultPendingOrder();
            UpdateCartItemRequest request = CartItemFixture.createUpdateCartItemRequestWithQuantity(quantity);

            // expected
            assertThatThrownBy(() -> pendingOrder.updateCartItem(request))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}