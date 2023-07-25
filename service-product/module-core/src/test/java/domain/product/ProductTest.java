package domain.product;

import fixture.product.BenefitFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class ProductTest {

    private static final Long SELLER_ID = 1L;
    private static final Long CODE = 111111111L;
    private static final int PRICE = 1000;
    private static final int SALE_PRICE = 900;
    private static final String NAME = "상품명";
    private static final float RATING = 2.5F;
    private static final String DETAIL_PAGE_URL = "localhost:10000/asset/image.jpg";
    private static final String DELIVERY = "착불/선결제";
    private static final int REVIEW_COUNT = 10;
    private static final int BUY_SATISFY = 90;
    private static final String IS_MINOR = "N";


    @Nested
    @DisplayName("Product 등록 테스트")
    public class ProductRegisterTest {

    @Test
    @DisplayName("Product가 성공적으로 만들어진다.")
    void successCreateProuct() {
        Benefit benefit = BenefitFixture.createDefaultBenefit();
        Product product = Product.builder()
                .sellerId(SELLER_ID)
                .code(CODE)
                .price(PRICE)
                .salePrice(SALE_PRICE)
                .name(NAME)
                .rating(RATING)
                .detailPageUrl(DETAIL_PAGE_URL)
                .delivery(DELIVERY)
                .reviewCount(REVIEW_COUNT)
                .buySatisfy(BUY_SATISFY)
                .isMinor(IS_MINOR)
                .benefit(benefit)
                .build();
        Assertions.assertThat(product.getSellerId()).isEqualTo(SELLER_ID);
        Assertions.assertThat(product.getCode()).isEqualTo(CODE);
        Assertions.assertThat(product.getPrice()).isEqualTo(PRICE);
        Assertions.assertThat(product.getSalePrice()).isEqualTo(SALE_PRICE);
        Assertions.assertThat(product.getName()).isEqualTo(NAME);
        Assertions.assertThat(product.getRating()).isEqualTo(RATING);
        Assertions.assertThat(product.getDetailPageUrl()).isEqualTo(DETAIL_PAGE_URL);
        Assertions.assertThat(product.getDelivery()).isEqualTo(DELIVERY);
        Assertions.assertThat(product.getReviewCount()).isEqualTo(REVIEW_COUNT);
        Assertions.assertThat(product.getBuySatisfy()).isEqualTo(BUY_SATISFY);
        Assertions.assertThat(product.getIsMinor()).isEqualTo(IS_MINOR);
        Assertions.assertThat(product.getBenefit()).isEqualTo(benefit);
    }

    @Test
    @DisplayName("Product salePrice가 price보다 큰 경우 예외가 발생한다.")
    void failRegisterProductWithInvalidSalePrice() {
        Benefit benefit = BenefitFixture.createDefaultBenefit();
        Assertions.assertThatThrownBy(() -> Product.builder()
                .sellerId(SELLER_ID)
                .code(CODE)
                .price(PRICE)
                .salePrice(PRICE + 100)
                .name(NAME)
                .rating(RATING)
                .detailPageUrl(DETAIL_PAGE_URL)
                .delivery(DELIVERY)
                .reviewCount(REVIEW_COUNT)
                .buySatisfy(BUY_SATISFY)
                .isMinor(IS_MINOR)
                .benefit(benefit)
                .build()).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 가격은 가격보다 작아야 합니다.");
    }

    @Test
    @DisplayName("Product의 가격과 할인 가격을 잘 못 설정한 경우 예외가 발생한다.")
    void failRegisterProductWithInvalidPriceAndDiscountPrice() {
        Benefit benefit = BenefitFixture.createDefaultBenefit();
        Assertions.assertThatThrownBy(() -> Product.builder()
                .sellerId(SELLER_ID)
                .code(CODE)
                .price(-1)
                .salePrice(3000)
                .name(NAME)
                .rating(RATING)
                .detailPageUrl(DETAIL_PAGE_URL)
                .delivery(DELIVERY)
                .reviewCount(REVIEW_COUNT)
                .buySatisfy(BUY_SATISFY)
                .isMinor(IS_MINOR)
                .benefit(benefit)
                .build()).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("가격은 0보다 커야 합니다.");

        Assertions.assertThatThrownBy(() -> Product.builder()
                        .sellerId(SELLER_ID)
                        .code(CODE)
                        .price(2000)
                        .salePrice(-1)
                        .name(NAME)
                        .rating(RATING)
                        .detailPageUrl(DETAIL_PAGE_URL)
                        .delivery(DELIVERY)
                        .reviewCount(REVIEW_COUNT)
                        .buySatisfy(BUY_SATISFY)
                        .isMinor(IS_MINOR)
                        .benefit(benefit)
                        .build()).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 가격은 0보다 커야 합니다.");

        Assertions.assertThatThrownBy(() -> Product.builder()
                        .sellerId(SELLER_ID)
                        .code(CODE)
                        .price(2000)
                        .salePrice(3000)
                        .name(NAME)
                        .rating(RATING)
                        .detailPageUrl(DETAIL_PAGE_URL)
                        .delivery(DELIVERY)
                        .reviewCount(REVIEW_COUNT)
                        .buySatisfy(BUY_SATISFY)
                        .isMinor(IS_MINOR)
                        .benefit(benefit)
                        .build()).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 가격은 가격보다 작아야 합니다.");
        }
    }
}