package src.main.java.com.github.msafriends.modulebatch.config.strategy;

import lombok.Builder;
import lombok.Getter;

@Getter
public class QueryStrategyParams {
    private final String tableName;
    private final String[] columnNames;
    private final String[] columnValues;

    @Builder
    public QueryStrategyParams(String tableName, String[] columnNames, String[] columnValues) {
        this.tableName = tableName;
        this.columnNames = columnNames;
        this.columnValues = columnValues;
    }
}
