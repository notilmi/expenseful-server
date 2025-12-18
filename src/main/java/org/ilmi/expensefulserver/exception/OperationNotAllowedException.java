package org.ilmi.expensefulserver.exception;

import org.springframework.http.HttpStatus;

public class OperationNotAllowedException extends ExceptionWithStatus {
    public OperationNotAllowedException(String message) {
        super(
                HttpStatus.NOT_ACCEPTABLE,
                message,
                "OPERATION_NOT_ALLOWED"
        );
    }

    public OperationNotAllowedException() {
        this("The requested operation is not allowed.");
    }
}
