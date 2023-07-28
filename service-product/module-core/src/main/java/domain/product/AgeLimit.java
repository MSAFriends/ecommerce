package domain.product;

public enum AgeLimit {
	MINOR("미성년자"), ADULT("성인");

	private final String description;

	AgeLimit(final String description) {
		this.description = description;
	}
}
