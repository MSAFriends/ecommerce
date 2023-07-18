package com.github.msafriends.serviceorder.modulecore.domain.bank;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class BankProviderTest {

    @Nested
    @DisplayName("은행 점검 시간 테스트")
    class IsBankMaintenanceTimeTest {
        private static final LocalTime MAINTENANCE_START_TIME = LocalTime.of(0, 0);
        private static final LocalTime MAINTENANCE_END_TIME = LocalTime.of(1, 0);
        private static final String BANK_NAME = "OO은행";

        @Test
        @DisplayName("은행 점검 시간인 경우 실패한다.")
        void testIsBankMaintenanceTime() {
            // given
            LocalTime now = LocalTime.of(0, 30);
            BankProvider bankProvider = BankProvider.builder()
                    .name(BANK_NAME)
                    .maintenanceStartTime(MAINTENANCE_START_TIME)
                    .maintenanceEndTime(MAINTENANCE_END_TIME)
                    .build();

            // when
            boolean isMaintenanceTime = bankProvider.isMaintenanceTime(now);

            // then
            assertThat(isMaintenanceTime).isTrue();
        }

        @Test
        @DisplayName("은행 점검 시간이 아닌 경우 성공한다.")
        void testIsNotBankMaintenanceTime() {
            // given
            BankProvider bankProvider = BankProvider.builder()
                    .name(BANK_NAME)
                    .maintenanceStartTime(MAINTENANCE_START_TIME)
                    .maintenanceEndTime(MAINTENANCE_END_TIME)
                    .build();

            // when
            boolean isMaintenanceTime = bankProvider.isMaintenanceTime(MAINTENANCE_END_TIME.plusMinutes(1));

            // then
            assertThat(isMaintenanceTime).isFalse();
        }
    }
}