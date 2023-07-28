package common.fixture.category;

import domain.category.Category;

public class CategoryFixture {
	public static final Long TEST_CATEGORY_ID = 1L;
	private static final String MAIN_CATEGORY_NAME = "가전제품";
	private static final String SUB_CATEGORY_NAME = "PC";
	private static final String MINOR_CATEGORY_NAME = "중고 제품";

	public static final Category MAIN_CATEGORY = createMainCategory();

	public static Category createMainCategory(){
		return Category.builder()
			.categoryName(MAIN_CATEGORY_NAME)
			.build();
	}

	public static Category createSubCategory(){
		return Category.builder()
			.categoryName(MAIN_CATEGORY_NAME)
			.category(MAIN_CATEGORY)
			.build();
	}


	public static Category createMinorCategory(){
		return Category.builder()
			.categoryName(MAIN_CATEGORY_NAME)
			.category(MAIN_CATEGORY)
			.build();
	}
}
