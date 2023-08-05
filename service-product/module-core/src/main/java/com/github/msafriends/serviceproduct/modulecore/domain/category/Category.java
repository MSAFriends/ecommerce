package com.github.msafriends.serviceproduct.modulecore.domain.category;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.util.Assert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "categories")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
	@Id
	@Column(name = "category_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "parent_id")
	private Category parentCategory;
	@Column(unique = true, nullable = false)
	private String categoryName;

	@Builder
	public Category(String categoryName, Category category) {
		validateNotNull(categoryName);
		this.categoryName = categoryName;
		this.parentCategory = category;
	}

	public void assignParentCategory(Category parentCategory){
		this.parentCategory = parentCategory;
	}

	private void validateNotNull(String categoryName){
		Assert.notNull(categoryName, "Category name should not be null.");
	}
}
