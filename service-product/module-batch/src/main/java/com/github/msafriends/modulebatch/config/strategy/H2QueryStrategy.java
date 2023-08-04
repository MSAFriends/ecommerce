package com.github.msafriends.modulebatch.config.strategy;

public class H2QueryStrategy implements QueryStrategy{
    private final String tableName;
    private final String[] columnNames;
    private final String[] columnValues;
    private final String uniqueColumnName;

    public H2QueryStrategy(final QueryStrategyParams queryStrategyParams) {
        this.tableName = queryStrategyParams.getTableName();
        this.columnNames = queryStrategyParams.getColumnNames();
        this.columnValues = queryStrategyParams.getColumnValues();
        this.uniqueColumnName = queryStrategyParams.getUniqueColumnName();
    }

    @Override
    public String getInsertSQL() {
        String columns = String.join(", ", columnNames);
        String values = String.join(", ", columnValues);
        if(tableName.equals("products")){
            return String.format("MERGE INTO %s (%s) KEY (%s) VALUES (%s)", tableName, columns, uniqueColumnName, values);
        }else {
            return String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, columns, values);
        }
    }
}
