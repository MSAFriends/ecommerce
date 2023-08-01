package com.github.msafriends.modulebatch.config.strategy;

import lombok.Getter;

@Getter
public enum DatabaseProfileType {

    LOCAL("local", "h2"),
    DEV("dev", "MySQL"),
    PROD("prod", "MySQL");

    private final String applicationProfile;
    private final String databaseType;

    DatabaseProfileType (final String applicationProfile, final String databaseType) {
        this.applicationProfile = applicationProfile;
        this.databaseType = databaseType;
    }
}
