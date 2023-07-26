package domain.review;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SellerReviewTest {

    private static final int RATING = 5;
    private static final String TITLE = "판매자 후기";
    private static final String CONTENT = "판매자 후기 내용";
    @Test
    @DisplayName("판매자 후기 등록이 성공적으로 이루어 진다.")
    void successRegisterSellerReview() {
        SellerReview sellerReview = SellerReview.builder()
                .rating(5)
                .title("판매자 후기")
                .content("판매자 후기 내용")
                .build();
        Assertions.assertThat(sellerReview.getRating()).isEqualTo(RATING);
        Assertions.assertThat(sellerReview.getTitle()).isEqualTo(TITLE);
        Assertions.assertThat(sellerReview.getContent()).isEqualTo(CONTENT);
    }

    @Test
    @DisplayName("판매자 후기 등록시 평점을 0 ~ 5점 사이로 하지 않으면 예외가 발생한다.")
    void failRegisterSellerReviewWithInvalidRating() {
        Assertions.assertThatThrownBy(() -> {
            SellerReview.builder()
                    .rating(6)
                    .title(TITLE)
                    .content(CONTENT)
                    .build();
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("평점은 0 ~ 5점 사이로 입력해주세요.");
    }
}
