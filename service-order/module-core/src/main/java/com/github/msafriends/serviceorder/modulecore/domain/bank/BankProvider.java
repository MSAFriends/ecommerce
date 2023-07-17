package com.github.msafriends.serviceorder.modulecore.domain.bank;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(nullable = false)
    private Integer maintenanceStartTime;

    @Column(nullable = false)
    private Integer maintenanceEndTime;

    @Builder
    public BankProvider(String name, Integer maintenanceStartTime, Integer maintenanceEndTime) {
        this.name = name;
        this.maintenanceStartTime = maintenanceStartTime;
        this.maintenanceEndTime = maintenanceEndTime;
    }
}
