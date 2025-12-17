package org.ilmi.expensefulserver.exception;

import org.springframework.http.HttpStatus;

public class EmailAlreadyExistsException extends ExceptionWithStatus {
    public EmailAlreadyExistsException(String message) {
        super(
                HttpStatus.NOT_FOUND,
                message,
                "EMAIL_EXISTS"
        );
    }

    public EmailAlreadyExistsException() {
        this("Email already in use");
    }
}
