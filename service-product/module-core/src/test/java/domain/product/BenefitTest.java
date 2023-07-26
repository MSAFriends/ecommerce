package domain.product;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BenefitTest {

    @Test
    @DisplayName("할인이 0과 100 사이가 아닌 경우 예외가 발생한다.")
    void failCreateBenefitWithInvalidDiscount() {
        Assertions.assertThatThrownBy(() -> Benefit.builder()
                .discount(-1)
                .build()).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인은 0과 100사이입니다.");

        Assertions.assertThatThrownBy(() -> Benefit.builder()
                        .discount(101)
                        .build()).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인은 0과 100사이입니다.");
    }

    @Test
    @DisplayName("마일리지가 0보다 작은 경우 예외가 발생한다.")
    void failCreateBenefitWithInvalidMileage() {
        Assertions.assertThatThrownBy(() -> Benefit.builder()
                .mileage(-1)
                .build()).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("마일리지는 0이상입니다.");
    }
}
