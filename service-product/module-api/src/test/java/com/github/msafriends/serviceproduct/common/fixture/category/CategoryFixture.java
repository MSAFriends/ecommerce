package com.github.msafriends.serviceproduct.common.fixture.category;

import java.util.ArrayList;
import java.util.List;

import org.junit.platform.commons.util.ReflectionUtils;
import org.springframework.test.util.ReflectionTestUtils;

import com.github.msafriends.serviceproduct.moduleapi.dto.CategoryRequest;
import com.github.msafriends.serviceproduct.modulecore.domain.category.Category;

public class CategoryFixture {
	public static final Long MAIN_CATEGORY_ID_A = 1L;
	public static final Long MAIN_CATEGORY_ID_B = 2L;
	public static final Long SUB_CATEGORY_ID_A = 3L;
	public static final Long SUB_CATEGORY_ID_B = 4L;
	public static final Long SUB_CATEGORY_ID_C = 5L;
	public static final Long SUB_CATEGORY_ID_D = 6L;
	public static final Long TEST_MINOR_CATEGORY_ID = 17L;
	public static final String MAIN_CATEGORY_NAME = "가전제품";
	public static final String SUB_CATEGORY_NAME = "PC";
	public static final String MINOR_CATEGORY_NAME = "중고 PC";
	public static final Category MAIN_CATEGORY = createMainCategory(MAIN_CATEGORY_NAME);
	public static final Category MAIN_CATEGORY_WITH_ID = createMainCategoryWithId(MAIN_CATEGORY_ID_A, MAIN_CATEGORY_NAME);
	public static final Category SUB_CATEGORY = createSubCategory(SUB_CATEGORY_NAME, MAIN_CATEGORY_ID_A);
	public static final Category SUB_CATEGORY_WITH_ID = createSubCategoryWithId(SUB_CATEGORY_ID_A, SUB_CATEGORY_NAME, MAIN_CATEGORY_ID_A);
	public static final Category MINOR_CATEGORY = createMinorCategory(MINOR_CATEGORY_NAME, SUB_CATEGORY_ID_A, MAIN_CATEGORY_ID_A);
	public static final Category MINOR_CATEGORY_WITH_ID = createMinorCategoryWithId(TEST_MINOR_CATEGORY_ID, MINOR_CATEGORY_NAME, SUB_CATEGORY_ID_A, MAIN_CATEGORY_ID_A);

	public static List<Category> createEntireCategoryTree(){
		List<Category> categoryTree = new ArrayList<>();
		List<Category> mainCategories = createMainCategoryWithIdList(1, 3);
		List<Category> subCategoriesA = createSubCategoryListWithParentA(3, 5);
		List<Category> subCategoriesB = createSubCategoryListWithParentB(5, 7);
		List<Category> minorCategoriesA = createMinorCategoryWithParentA(7, 10);
		List<Category> minorCategoriesB = createMinorCategoryWithParentB(10, 13);
		List<Category> minorCategoriesC = createMinorCategoryWithParentC(13, 16);
		List<Category> minorCategoriesD = createMinorCategoryWithParentD(16, 18);
		categoryTree.addAll(mainCategories);
		categoryTree.addAll(subCategoriesA);
		categoryTree.addAll(subCategoriesB);
		categoryTree.addAll(minorCategoriesA);
		categoryTree.addAll(minorCategoriesB);
		categoryTree.addAll(minorCategoriesC);
		categoryTree.addAll(minorCategoriesD);
		return categoryTree;
	}

	public static CategoryRequest createCategoryRequest(Long parentId){
		return CategoryRequest.builder()
			.parentCategoryId(parentId)
			.categoryName(MINOR_CATEGORY_NAME)
			.build();
	}

	public static Category createMainCategory(final String categoryName){
		return Category.builder()
			.categoryName(categoryName)
			.build();
	}

	public static Category createSubCategory(final String minorCategoryName, final long parentId){
		return Category.builder()
			.categoryName(minorCategoryName)
			.category(createMainCategoryWithId(parentId, MAIN_CATEGORY_NAME + parentId))
			.build();
	}

	public static Category createMinorCategory(final String minorCategoryName, final Long parentId, final Long rootId){
		return Category.builder()
			.categoryName(minorCategoryName)
			.category(createSubCategoryWithId(parentId, minorCategoryName, rootId))
			.build();
	}

	public static Category createMainCategoryWithId(final Long id, final String categoryName){
		Category category = createMainCategory(categoryName);
		ReflectionTestUtils.setField(category, "id", id);
		return category;
	}

	public static List<Category> createMainCategoryWithIdList(final int from, final int to){
		List<Category> categories = new ArrayList<>();
		for (int i = from; i < to; i++) {
			categories.add(createMainCategoryWithId((long)i, "main category : " + i));
		}
		return categories;
	}

	public static Category createSubCategoryWithId(final Long id, final String categoryName, final Long parentId){
		Category category = createSubCategory(categoryName, parentId);
		ReflectionTestUtils.setField(category, "id", id);
		return category;
	}

	public static List<Category> createSubCategoryListWithParentA(final int from, final int to){
		List<Category> subCategories = new ArrayList<>();
		for (int i = from; i < to; i++) {
			subCategories.add(createSubCategoryWithId((long)i, "sub category : " + i, MAIN_CATEGORY_ID_A));
		}
		return subCategories;
	}

	public static List<Category> createSubCategoryListWithParentB(final int from, final int to){
		List<Category> subCategories = new ArrayList<>();
		for (int i = from; i < to; i++) {
			subCategories.add(createSubCategoryWithId((long)i, "sub category : " + i, MAIN_CATEGORY_ID_B));
		}
		return subCategories;
	}

	public static Category createMinorCategoryWithId(final Long id, final String categoryName, final Long parentId, final Long rootId){
		Category category = createMinorCategory(categoryName, parentId, rootId);
		ReflectionTestUtils.setField(category, "id", id);
		return category;
	}

	public static List<Category> createMinorCategoryWithParentA(final int from, final int to){
		List<Category> minorCategories = new ArrayList<>();
		for (int i = from; i < to; i++) {
			minorCategories.add(createMinorCategoryWithId((long)i, "minor category : " + i, SUB_CATEGORY_ID_A, MAIN_CATEGORY_ID_A));
		}
		return minorCategories;
	}

	public static List<Category> createMinorCategoryWithParentB(final int from, final int to){
		List<Category> minorCategories = new ArrayList<>();
		for (int i = from; i < to; i++) {
			minorCategories.add(createMinorCategoryWithId((long)i, "minor category : " + i,SUB_CATEGORY_ID_B, MAIN_CATEGORY_ID_A));
		}
		return minorCategories;
	}

	public static List<Category> createMinorCategoryWithParentC(final int from, final int to){
		List<Category> minorCategories = new ArrayList<>();
		for (int i = from; i < to; i++) {
			minorCategories.add(createMinorCategoryWithId((long)i, "minor category : " + i,SUB_CATEGORY_ID_C, MAIN_CATEGORY_ID_B));
		}
		return minorCategories;
	}

	public static List<Category> createMinorCategoryWithParentD(final int from, final int to){
		List<Category> minorCategories = new ArrayList<>();
		for (int i = from; i < to; i++) {
			minorCategories.add(createMinorCategoryWithId((long)i, "minor category : " + i,SUB_CATEGORY_ID_D, MAIN_CATEGORY_ID_B));
		}
		return minorCategories;
	}
}
