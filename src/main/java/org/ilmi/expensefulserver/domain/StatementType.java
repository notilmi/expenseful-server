package org.ilmi.expensefulserver.domain;

import lombok.Getter;

@Getter
public enum StatementType {
    INCOME("Income"),
    EXPENSE("Expense");

    private final String content;

    StatementType(String content) {
        this.content = content;
    }

}
