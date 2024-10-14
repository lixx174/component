package com.jinx.component.domain.data;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Jinx
 */
@Getter
@Setter
public class Example {
    private String column;
    private Operation operation;
    private Object value;

    public Example(String column, Operation operation, Object value) {
        if (column == null || column.isEmpty()) {
            throw new IllegalArgumentException("column must have text");
        }
        if (operation == null) {
            throw new IllegalArgumentException("operation must not be null");
        }

        this.column = column;
        this.value = value;
        this.operation = operation;
    }
}
