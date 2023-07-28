package domain;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.github.msafriends.modulecommon.exception.ErrorCode;
import com.github.msafriends.modulecommon.exception.InvalidValueException;

import domain.product.Product;
import fixture.product.ProductFixture;

public class ProductTest {
    @Nested
    @DisplayName("Product 엔티티 테스트")
    class ProductRegisterTest {
        @Test
        @DisplayName("Product 생성 성공 케이스")
        void successCreateProuct() {
            Product product = Product.builder()
                .sellerId(ProductFixture.SELLER_ID)
                .code(ProductFixture.CODE)
                .price(ProductFixture.createDefaultPrice())
                .name(ProductFixture.NAME)
                .delivery(ProductFixture.DELIVERY)
                .buySatisfy(ProductFixture.BUY_SATISFY)
                .ageLimit(ProductFixture.AGE_LIMIT)
                .benefit(ProductFixture.DEFAULT_BENEFIT)
                .size(ProductFixture.DEFAULT_SIZE)
                .build();
            Assertions.assertThat(product.getSellerId()).isEqualTo(ProductFixture.SELLER_ID);
            Assertions.assertThat(product.getCode()).isEqualTo(ProductFixture.CODE);
            Assertions.assertThat(product.getPrice().getPriceValue()).isEqualTo(ProductFixture.PRICE_VALUE);
            Assertions.assertThat(product.getPrice().getSalePriceValue()).isEqualTo(ProductFixture.SALE_PRICE_VALUE);
            Assertions.assertThat(product.getName()).isEqualTo(ProductFixture.NAME);
            Assertions.assertThat(product.getDelivery()).isEqualTo(ProductFixture.DELIVERY);
            Assertions.assertThat(product.getBuySatisfy()).isEqualTo(ProductFixture.BUY_SATISFY);
            Assertions.assertThat(product.getAgeLimit()).isEqualTo(ProductFixture.AGE_LIMIT);
            Assertions.assertThat(product.getSize()).isEqualTo(ProductFixture.DEFAULT_SIZE);
            Assertions.assertThat(product.getBenefit()).isEqualTo(ProductFixture.DEFAULT_BENEFIT);
        }

        @Test
        @DisplayName("Product salePrice가 price보다 큰 경우 예외가 발생한다.")
        void failRegisterProductWithInvalidSalePrice() {
            InvalidValueException invalidValueException = org.junit.jupiter.api.Assertions
                .assertThrows(InvalidValueException.class,
                    () -> ProductFixture.createInvalidPriceProduct(
                        ProductFixture.PRICE_VALUE, ProductFixture.PRICE_VALUE + ProductFixture.ADDITIONAL_PRICE_AMOUNT));
            Assertions.assertThat(ErrorCode.INVALID_PRICE_ERROR).isEqualTo(invalidValueException.getErrorCode());
            Assertions.assertThat("할인 가격은 가격보다 작아야 합니다.").isEqualTo(invalidValueException.getDetail());
        }

        @Test
        @DisplayName("Product의 가격은 음수를 가질 수 없다.")
        void invalidPriceTest() {
            InvalidValueException invalidValueException = org.junit.jupiter.api.Assertions.assertThrows(
                InvalidValueException.class,
                () -> ProductFixture.createInvalidPriceProduct(
                    ProductFixture.NEGATIVE_PRICE_VALUE, ProductFixture.SALE_PRICE_VALUE));
            Assertions.assertThat(invalidValueException.getErrorCode()).isEqualTo(ErrorCode.INVALID_PRICE_ERROR);
            Assertions.assertThat(invalidValueException.getDetail()).isEqualTo("가격은 0보다 커야 합니다.");
        }
        @Test
        @DisplayName("Product의 할인 가격은 음수를 가질 수 없다.")
        void invalidSalePriceTest() {
            InvalidValueException invalidValueException = org.junit.jupiter.api.Assertions.assertThrows(
                InvalidValueException.class,
                () -> ProductFixture.createInvalidPriceProduct(
                    ProductFixture.PRICE_VALUE, ProductFixture.NEGATIVE_PRICE_VALUE));
            Assertions.assertThat(invalidValueException.getErrorCode()).isEqualTo(ErrorCode.INVALID_PRICE_ERROR);
            Assertions.assertThat(invalidValueException.getDetail()).isEqualTo("할인 가격은 0보다 커야 합니다.");
        }
    }
}