package org.ilmi.expensefulserver.exception;

import org.springframework.http.HttpStatus;

public class InvalidOperationException extends ExceptionWithStatus {
    public InvalidOperationException(String message) {
        super(
                HttpStatus.NOT_FOUND,
                message,
                "INVALID_OPERATION"
        );
    }

    public InvalidOperationException() {
        this("The requested operation is invalid.");
    }
}
