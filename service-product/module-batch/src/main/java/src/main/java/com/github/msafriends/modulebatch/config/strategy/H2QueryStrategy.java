package src.main.java.com.github.msafriends.modulebatch.config.strategy;

public class H2QueryStrategy implements QueryStrategy{
    private final String tableName;
    private final String[] columnNames;
    private final String[] columnValues;

    public H2QueryStrategy(final QueryStrategyParams queryStrategyParams) {
        this.tableName = queryStrategyParams.getTableName();
        this.columnNames = queryStrategyParams.getColumnNames();
        this.columnValues = queryStrategyParams.getColumnNames();
    }

    @Override
    public String getInsertSQL() {
        String columns = String.join(", ", columnNames);
        String values = String.join(", ", columnValues);
        return String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, columns, values);
    }
}
