package domain.review;

import domain.product.Benefit;
import domain.product.Product;
import fixture.product.BenefitFixture;
import fixture.product.ProductFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReviewTest {

    private static final Long ID = 1L;
    private static final int RATING = 5;
    private static final String TITLE = "상품 후기";
    private static final String CONTENT = "상품 후기 내용";

    @Test
    @DisplayName("리뷰 등록이 성공적으로 이루어 진다.")
    void successRegisterReview() {
        Benefit benefit = BenefitFixture.createDefaultBenefit();
        Product product = ProductFixture.createProduct(benefit);
        Review review = Review.builder()
                .id(ID)
                .product(product)
                .rating(RATING)
                .title(TITLE)
                .content(CONTENT)
                .build();
        Assertions.assertThat(review.getId()).isEqualTo(ID);
        Assertions.assertThat(review.getRating()).isEqualTo(RATING);
        Assertions.assertThat(review.getTitle()).isEqualTo(TITLE);
        Assertions.assertThat(review.getContent()).isEqualTo(CONTENT);
    }
}
