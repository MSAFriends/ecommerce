package com.github.msafriends.modulebatch.config.strategy;

public class MySQLQueryStrategy implements QueryStrategy{
    private final String tableName;
    private final String[] columnNames;
    private final String[] columnValues;
    private final String uniqueColumnName;
    private final String uniqueColumnValue;

    public MySQLQueryStrategy(final QueryStrategyParams queryStrategyParams) {
        this.tableName = queryStrategyParams.getTableName();
        this.columnNames = queryStrategyParams.getColumnNames();
        this.columnValues = queryStrategyParams.getColumnValues();
        this.uniqueColumnName = queryStrategyParams.getUniqueColumnName();
        this.uniqueColumnValue = queryStrategyParams.getUniqueColumnValue();
    }

    @Override
    public String getInsertSQL() {
        String columns = String.join(", ", columnNames);
        String values = String.join(", ", columnValues);
        if(tableName.equals("product")){
            return String.format("INSERT INTO %s (%s) VALUES (%s) ON DUPLICATE KEY UPDATE %s = %s", tableName, columns, values, uniqueColumnName, uniqueColumnValue);
        }else {
            return String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, columns, values);
        }
    }
}
