package com.github.msafriends.serviceorder.modulecore.domain.card;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class CardExpirationTest {

    @Nested
    @DisplayName("카드 만료기한 테스트")
    class IsExpiredTest {
        private static final LocalDate CURRENT_DATE = LocalDate.of(2023, 7, 1);
        private static final LocalDate EXPIRATION_DATE = LocalDate.of(2023, 7, 31);
        private static final Integer CURRENT_YEAR = 23;
        private static final Integer PREVIOUS_MONTH = 6;
        private static final Integer CURRENT_MONTH = 7;
        private static final Integer NEXT_MONTH = 8;

        @Test
        @DisplayName("현재 날짜가 만료일 이전인 경우에 만료되지 않는다.")
        void testIsExpiredBefore() {
            var cardExpiration = CardExpiration.builder()
                    .year(CURRENT_YEAR)
                    .month(NEXT_MONTH)
                    .build();

            assertThat(cardExpiration.isExpired(CURRENT_DATE)).isFalse();
        }

        @Test
        @DisplayName("현재 날짜가 만료일과 같은 경우에 만료되지 않는다.")
        void testIsExpiredSame() {
            var cardExpiration = CardExpiration.builder()
                    .year(CURRENT_YEAR)
                    .month(CURRENT_MONTH)
                    .build();

            assertThat(cardExpiration.isExpired(CURRENT_DATE)).isFalse();
        }

        @Test
        @DisplayName("현재 날짜가 만료일 이후인 경우에는 만료된다.")
        void testIsExpiredAfter() {
            var cardExpiration = CardExpiration.builder()
                    .year(CURRENT_YEAR)
                    .month(PREVIOUS_MONTH)
                    .build();

            assertThat(cardExpiration.isExpired(CURRENT_DATE)).isTrue();
        }
    }
}