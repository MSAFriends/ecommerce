package domain;

import com.github.msafriends.serviceproduct.modulecore.domain.product.Product;
import com.github.msafriends.serviceproduct.modulecore.domain.review.ProductReview;
import fixture.product.ProductFixture;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductReviewTest {
    private static final Long MEMBER_ID = 1L;

    private static final int RATING = 5;
    private static final String TITLE = "상품 후기";
    private static final String CONTENT = "상품 후기 내용";

    @Test
    @DisplayName("리뷰 등록이 성공적으로 이루어 진다.")
    void successRegisterReview() {
        Product product = ProductFixture.createProduct();
        ProductReview productReview = ProductReview.builder()
                .memberId(MEMBER_ID)
                .product(product)
                .rating(RATING)
                .title(TITLE)
                .content(CONTENT)
                .build();
        Assertions.assertThat(productReview.getMemberId()).isEqualTo(MEMBER_ID);
        Assertions.assertThat(productReview.getRating()).isEqualTo(RATING);
        Assertions.assertThat(productReview.getTitle()).isEqualTo(TITLE);
        Assertions.assertThat(productReview.getContent()).isEqualTo(CONTENT);
    }

    @Test
    @DisplayName("리뷰 등록시 평점을 0 ~ 5점 사이로 하지 않으면 예외가 발생한다.")
    void failRegisterReviewWithInvalidRating() {
        Product product = ProductFixture.createProduct();
        Assertions.assertThatThrownBy(() -> {
            ProductReview.builder()
                    .memberId(MEMBER_ID)
                    .product(product)
                    .rating(6)
                    .title(TITLE)
                    .content(CONTENT)
                    .build();
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("평점은 0 ~ 5점 사이로 입력해주세요.");
    }
}
