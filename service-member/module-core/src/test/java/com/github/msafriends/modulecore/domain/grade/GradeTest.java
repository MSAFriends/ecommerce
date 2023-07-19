package com.github.msafriends.modulecore.domain.grade;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class GradeTest {
    private static final int BRONZE_MIN_ORDER = 0;
    private static final int BRONZE_MAX_ORDER = 4;

    @Nested
    @DisplayName("등급 등록 테스트")
    class GradeRegisterTests {

        @Test
        @DisplayName("등급 등록이 성공적으로 업데이트 된다.")
        void testRegisterGrade() {

            Grade bronzeGrade = Grade.builder()
                    .name("BRONZE")
                    .minOrderCountForGrade(BRONZE_MIN_ORDER)
                    .maxOrderCountForGrade(BRONZE_MAX_ORDER)
                    .build();
            assertThat(bronzeGrade.getName()).isEqualTo("BRONZE");
        }

        @Test
        @DisplayName("등급 등록시에 최소 주문수가 최대 주문수가 큰 경우 실패한다.")
        void testRegisterGradeWithInvalidOrderCount() {
            assertThatThrownBy(() ->
                    Grade.builder()
                            .name("BRONZE")
                            .minOrderCountForGrade(BRONZE_MAX_ORDER)
                            .maxOrderCountForGrade(BRONZE_MIN_ORDER)
                            .build())
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Invalid order counts: minOrderCount must be less than or equal to maxOrderCount.");
        }

        @Test
        @DisplayName("등급 등록시에 등급 이름이 지정이 되지 않으면 실패한다.")
        void testRegisterGradeWithBlankName() {
            assertThatThrownBy(() ->
                    Grade.builder()
                            .name("")
                            .minOrderCountForGrade(BRONZE_MIN_ORDER)
                            .maxOrderCountForGrade(BRONZE_MAX_ORDER)
                            .build())
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Grade name must not be null or empty.");
        }
    }
}