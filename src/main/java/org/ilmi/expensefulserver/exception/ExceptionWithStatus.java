package org.ilmi.expensefulserver.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ExceptionWithStatus extends RuntimeException {
    private final HttpStatus status;
    private final String code;

    public ExceptionWithStatus(HttpStatus status, String message, String code) {
        super(message);
        this.status = status;
        this.code = code;
    }
}
