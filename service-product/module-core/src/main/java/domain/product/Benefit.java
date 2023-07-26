package domain.product;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import static lombok.AccessLevel.PROTECTED;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Benefit {
    @Min(value = 0)
    @Max(value = 100)
    private int discount;
    @Min(value = 0, message = "마일리지는 0보다 커야합니다.")
    private int mileage;

    @Builder
    public Benefit(int discount, int mileage) {
        validateBenefit(discount, mileage);
        this.discount = discount;
        this.mileage = mileage;
    }

    private void validateBenefit(int discount, int mileage) {
        validateDiscount(discount);
        validateMileage(mileage);
    }

    private void validateDiscount(int discount) {
        Assert.isTrue(discount >= 0 && discount <= 100, "할인은 0과 100사이입니다.");
    }

    private void validateMileage(int mileage) {
        Assert.isTrue(mileage >= 0, "마일리지는 0이상입니다.");
    }
}
