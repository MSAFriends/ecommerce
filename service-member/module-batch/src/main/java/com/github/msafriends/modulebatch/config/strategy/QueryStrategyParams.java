package com.github.msafriends.modulebatch.config.strategy;

import lombok.Builder;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
public class QueryStrategyParams {
    private final String tableName;
    private final String[] columnNames;
    private final String[] columnValues;
    private final String uniqueColumnName;
    private final String uniqueColumnValue;

    @Builder
    public QueryStrategyParams(String tableName, String[] columnNames, String[] columnValues, String uniqueColumnName, String uniqueColumnValue) {
        this.tableName = tableName;
        this.columnNames = columnNames;
        this.columnValues = columnValues;
        this.uniqueColumnName = uniqueColumnName;
        this.uniqueColumnValue = uniqueColumnValue;
    }
}
