package domain.product;

import static lombok.AccessLevel.*;

import org.springframework.util.Assert;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Benefit {
    private int discount;
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
        Assert.isTrue(discount >= 0 && discount <= 100, "할인율은 0과 100사이입니다.");
    }

    private void validateMileage(int mileage) {
        Assert.isTrue(mileage >= 0, "마일리지는 0이상입니다.");
    }
}
