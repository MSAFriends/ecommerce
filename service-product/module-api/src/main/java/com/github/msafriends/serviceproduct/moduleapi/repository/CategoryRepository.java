package com.github.msafriends.serviceproduct.moduleapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.msafriends.modulecommon.exception.EntityNotFoundException;
import com.github.msafriends.modulecommon.exception.ErrorCode;

import com.github.msafriends.serviceproduct.modulecore.domain.category.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	default Category findByIdOrThrow(Long id){
		return findById(id)
			.orElseThrow(
				() -> new EntityNotFoundException(ErrorCode.INVALID_INPUT_VALUE, "해당 id를 가진 카테고리가 없습니다.")
			);
	}
}
