package fixture.review;

import domain.review.Review;

public class ReviewFixture {

    private static final int RATING = 5;
    private static final String TITLE = "상품 후기";
    private static final String CONTENT = "상품 후기 내용";

    public static Review createDefaultReview() {
        return Review.builder()
                .rating(RATING)
                .title(TITLE)
                .content(CONTENT)
                .build();
    }
}
