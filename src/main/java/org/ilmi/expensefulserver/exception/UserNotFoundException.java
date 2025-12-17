package org.ilmi.expensefulserver.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ExceptionWithStatus {
    public UserNotFoundException(String message) {
        super(
                HttpStatus.NOT_FOUND,
                message,
                "USER_NOT_FOUND"
        );
    }

    public UserNotFoundException() {
        this("The requested user was not found.");
    }
}
