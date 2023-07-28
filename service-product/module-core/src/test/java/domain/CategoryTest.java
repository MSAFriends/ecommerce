package domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import domain.category.Category;

class CategoryTest {
	@Nested
	@DisplayName("Category엔티티 테스트")
	public class CategoryEntityTest{
		@Test
		@DisplayName("카테고리 명은 null일 수 없다.")
		void categoryNameConstraintTest() throws Exception {
			assertThatThrownBy(() -> Category.builder().build())
				.isInstanceOf(IllegalArgumentException.class);
		}
	}
}