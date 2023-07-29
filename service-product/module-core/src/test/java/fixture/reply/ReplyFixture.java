package fixture.reply;

import com.github.msafriends.serviceproduct.modulecore.domain.review.ProductReview;
import com.github.msafriends.serviceproduct.modulecore.domain.review.Reply;
import fixture.review.ProductReviewFixture;

public class ReplyFixture {

	public static final Long TEST_ID = 1L;
	public static final String TEST_CONTENT = "너무 비싸요";
	public static final String INVALID_LENGTH_CONTENT = generateDummyText(510);
	public static final Long TEST_SELLER_ID = 1L;
	public static final ProductReview TEST_REVIEW = ProductReviewFixture.createDefaultReview();


	public Reply createDefaultReply(){
		return Reply.builder()
			.sellerId(TEST_SELLER_ID)
			.content(TEST_CONTENT)
			.review(TEST_REVIEW)
			.build();
	}

	public static String generateDummyText(int length) {
		if (length <= 0) {
			throw new IllegalArgumentException("Length must be greater than 0");
		}
		StringBuilder sb = new StringBuilder();
		int count = length / 10;
		for (int i = 0; i < count; i++) {
			sb.append("Lorem ipsum ");
		}
		int remaining = length % 10;
		if (remaining > 0) {
			sb.append("Lorem ipsum".substring(0, remaining));
		}
		return sb.toString();
	}

}
