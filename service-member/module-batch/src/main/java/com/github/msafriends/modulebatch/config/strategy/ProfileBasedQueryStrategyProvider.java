package com.github.msafriends.modulebatch.config.strategy;

import org.springframework.core.env.Environment;

public class ProfileBasedQueryStrategyProvider {
    private static final String LOCAL_ENV = "local";

    private final Environment environment;
    private final QueryStrategyParams queryStrategyParams;

    public ProfileBasedQueryStrategyProvider(Environment environment, QueryStrategyParams queryStrategyParams) {
        this.environment = environment;
        this.queryStrategyParams = queryStrategyParams;
    }

    public String getQuery() {
        String profile = environment.getActiveProfiles()[0];

        if (profile.equals(LOCAL_ENV)) {
            return new H2QueryStrategy(queryStrategyParams).getInsertSQL();
        }
        return new MySQLQueryStrategy(queryStrategyParams).getInsertSQL();
    }
}
