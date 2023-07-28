package common.fixture.review;

import domain.product.Product;
import domain.review.ProductReview;
import common.fixture.product.ProductFixture;

public class ProductReviewFixture {
    private static final Long MEMBER_ID = 1L;
    private static final int RATING = 5;
    private static final String TITLE = "상품 후기";
    private static final String CONTENT = "상품 후기 내용";
    private static final Product TEST_PRODUCT = ProductFixture.createProduct();

    public static ProductReview createDefaultReview() {
        return ProductReview.builder()
                .memberId(MEMBER_ID)
                .rating(RATING)
                .title(TITLE)
                .content(CONTENT)
                .product(TEST_PRODUCT)
                .build();
    }
}
