package domain.review;

import domain.product.Benefit;
import domain.product.Product;
import fixture.product.BenefitFixture;
import fixture.product.ProductFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReviewTest {
    private static final Long MEMBER_ID = 1L;

    private static final int RATING = 5;
    private static final String TITLE = "상품 후기";
    private static final String CONTENT = "상품 후기 내용";

    @Test
    @DisplayName("리뷰 등록이 성공적으로 이루어 진다.")
    void successRegisterReview() {
        Benefit benefit = BenefitFixture.createDefaultBenefit();
        Product product = ProductFixture.createProduct(benefit);
        Review review = Review.builder()
                .memberId(MEMBER_ID)
                .product(product)
                .rating(RATING)
                .title(TITLE)
                .content(CONTENT)
                .build();
        Assertions.assertThat(review.getMemberId()).isEqualTo(MEMBER_ID);
        Assertions.assertThat(review.getRating()).isEqualTo(RATING);
        Assertions.assertThat(review.getTitle()).isEqualTo(TITLE);
        Assertions.assertThat(review.getContent()).isEqualTo(CONTENT);
    }

    @Test
    @DisplayName("리뷰 등록시 평점을 0 ~ 5점 사이로 하지 않으면 예외가 발생한다.")
    void failRegisterReviewWithInvalidRating() {
        Benefit benefit = BenefitFixture.createDefaultBenefit();
        Product product = ProductFixture.createProduct(benefit);
        Assertions.assertThatThrownBy(() -> {
            Review.builder()
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
