package fixture.product;

import domain.product.Benefit;

public class BenefitFixture {

    private static final int DISCOUNT = 10;
    private static final int MILEAGE = 10;

    public static Benefit createDefaultBenefit() {
        return Benefit.builder()
                .discount(DISCOUNT)
                .mileage(MILEAGE)
                .build();
    }
}
