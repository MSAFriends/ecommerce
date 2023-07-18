package com.github.msafriends.serviceorder.modulecore.domain.bank;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.time.LocalTime;

@Entity
@Getter
@Table(name = "bank_providers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BankProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_provider_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, columnDefinition = "TIME")
    private LocalTime maintenanceStartTime;

    @Column(nullable = false, columnDefinition = "TIME")
    private LocalTime maintenanceEndTime;

    @Builder
    public BankProvider(String name, LocalTime maintenanceStartTime, LocalTime maintenanceEndTime) {
        validateBankProvider(name, maintenanceStartTime, maintenanceEndTime);

        this.name = name;
        this.maintenanceStartTime = maintenanceStartTime;
        this.maintenanceEndTime = maintenanceEndTime;
    }

    public boolean isMaintenanceTime(LocalTime currentTime) {
        return currentTime.isAfter(maintenanceStartTime) && currentTime.isBefore(maintenanceEndTime);
    }

    private void validateBankProvider(String name, LocalTime maintenanceStartTime, LocalTime maintenanceEndTime) {
        validateNotNull(name, maintenanceStartTime, maintenanceEndTime);
        validateMaintenanceTime(maintenanceStartTime, maintenanceEndTime);
    }

    private void validateNotNull(String name, LocalTime maintenanceStartTime, LocalTime maintenanceEndTime) {
        Assert.hasText(name, "name must not be empty");
        Assert.notNull(maintenanceStartTime, "maintenanceStartTime must not be null");
        Assert.notNull(maintenanceEndTime, "maintenanceEndTime must not be null");
    }

    private void validateMaintenanceTime(LocalTime maintenanceStartTime, LocalTime maintenanceEndTime) {
        Assert.isTrue(!maintenanceStartTime.isAfter(maintenanceEndTime),
                "maintenanceStartTime must be before maintenanceEndTime");
    }
}
