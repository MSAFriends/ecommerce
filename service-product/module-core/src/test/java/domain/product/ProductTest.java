package domain.product;

import fixture.product.BenefitFixture;
import fixture.product.ProductFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProductTest {

    private static final Long PRODUCT_ID = 1L;
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
    @Test
    @DisplayName("Product가 성공적으로 만들어진다.")
    void successCreateProuct() {
        Benefit benefit = BenefitFixture.createDefaultBenefit();
        Product product = Product.builder()
                .id(PRODUCT_ID)
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
        Assertions.assertThat(product.getId()).isEqualTo(PRODUCT_ID);
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
}
