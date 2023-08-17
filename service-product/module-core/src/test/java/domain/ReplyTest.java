package domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


import com.github.msafriends.serviceproduct.modulecore.domain.review.Reply;
import com.github.msafriends.serviceproduct.modulecore.exception.InvalidValueException;

import fixture.reply.ReplyFixture;

class ReplyTest {
	@Nested
	@DisplayName("Reply엔티티 필드 유효성 검사")
	class ReplyValidationTest{
		@Test
		@DisplayName("content는 null일 수 없다.")
		void contentNotNullTest() throws Exception {
			//when
			Reply.ReplyBuilder builder = Reply.builder()
				.sellerId(ReplyFixture.TEST_SELLER_ID)
				.review(ReplyFixture.TEST_REVIEW);
			//then
			Assertions.assertThrows(InvalidValueException.class, builder::build);
		}

		@Test
		@DisplayName("content의 길이는 500자를 넘어선 안된다.")
		void contentLengthTest() throws Exception {
			//when
			Reply.ReplyBuilder builder = Reply.builder()
				.sellerId(ReplyFixture.TEST_SELLER_ID)
				.content(ReplyFixture.generateDummyText(501))
				.review(ReplyFixture.TEST_REVIEW);
			//then
			Assertions.assertThrows(InvalidValueException.class, builder::build);
		}

		@Test
		@DisplayName("sellerId는 null이어선 안된다.")
		void sellerIdFieldValidationTest() throws Exception {
			//when
			Reply.ReplyBuilder builder = Reply.builder()
				.content(ReplyFixture.TEST_CONTENT)
				.review(ReplyFixture.TEST_REVIEW);
			//then
			Assertions.assertThrows(InvalidValueException.class, builder::build);
		}

		@Test
		@DisplayName("review 대상이 있어야 reply를 달 수 있다.")
		void reviewFieldValidationTest() throws Exception {
			//when
			Reply.ReplyBuilder builder = Reply.builder()
				.sellerId(ReplyFixture.TEST_SELLER_ID)
				.content(ReplyFixture.TEST_CONTENT);
			//then
			Assertions.assertThrows(InvalidValueException.class, builder::build);
		}
	}
}
