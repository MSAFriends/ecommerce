package com.github.msafriends.serviceproduct.modulecore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.github.msafriends.serviceproduct.modulecore.domain.category.Category;
import com.github.msafriends.serviceproduct.modulecore.exception.EntityNotFoundException;
import com.github.msafriends.serviceproduct.modulecore.exception.ErrorCode;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	default Category findByIdOrThrow(Long id){
		return findById(id)
			.orElseThrow(
				() -> new EntityNotFoundException(ErrorCode.INVALID_INPUT_VALUE, "해당 id를 가진 카테고리가 없습니다.")
			);
	}
	List<Category> findAllByParentCategoryId(Long parentId);
	@Query("SELECT c FROM Category c WHERE c.parentCategory IS NULL")
	List<Category> findAllParentCategories();
}
