package com.github.msafriends.modulebatch.csv;

import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;

public class CSVFieldFormatter<T> extends BeanWrapperFieldExtractor<T> {
    private final String delimiter;

    private CSVFieldFormatter(String delimiter, String[] names) {
        this.delimiter = delimiter;
        super.setNames(names);
    }

    @Override
    public Object[] extract(T item) {
        Object[] values = super.extract(item);
        for (int i = 0; i < values.length; i++) {
            if (values[i] instanceof String value) {
                values[i] = formatValue(value);
            }
        }
        return values;
    }

    private String formatValue(String value) {
        if (value.contains(delimiter)) {
            return "\"" + value + "\"";
        }
        return value;
    }

    public static class Builder<T> {
        private String delimiter = ",";
        private String[] names;

        public Builder<T> delimiter(String delimiter) {
            this.delimiter = delimiter;
            return this;
        }

        public Builder<T> names(String... names) {
            this.names = names;
            return this;
        }

        public CSVFieldFormatter<T> build() {
            return new CSVFieldFormatter<>(this.delimiter, names);
        }
    }
}
