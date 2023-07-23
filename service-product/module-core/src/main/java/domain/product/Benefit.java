package domain.product;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Benefit {
    private int discount;
    private int mileage;

    @Builder
    public Benefit(int discount, int mileage) {
        this.discount = discount;
        this.mileage = mileage;
    }
}
