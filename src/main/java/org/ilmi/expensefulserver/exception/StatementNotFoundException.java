package org.ilmi.expensefulserver.exception;

import org.springframework.http.HttpStatus;

public class StatementNotFoundException extends ExceptionWithStatus {
    public StatementNotFoundException(String message) {
        super(
                HttpStatus.NOT_FOUND,
                message,
                "STATEMENT_NOT_FOUND"
        );
    }

    public StatementNotFoundException() {
        this("The requested statement was not found.");
    }
}
