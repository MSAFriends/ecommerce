package domain.category;

import org.springframework.util.Assert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

	@Id
	@Column(name = "category_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String categoryName;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private Category parentCategory;

	@Builder
	public Category(String categoryName, Category category) {
		validateNotNull(categoryName);
		this.categoryName = categoryName;
		this.parentCategory = category;
	}

	private void validateNotNull(String categoryName){
		Assert.notNull(categoryName, "Category name should not be null.");
	}
}
