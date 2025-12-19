package org.ilmi.expensefulserver.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.ilmi.expensefulserver.input.web.data.output.ErrorResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AppExceptionHandler {
    Logger logger = LoggerFactory.getLogger(AppExceptionHandler.class);


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleException(Exception ex) {
        logger.error("Exception: ", ex);

        return ResponseEntity.status(500).body(
                ErrorResponseDTO
                        .builder()
                        .code("INTERNAL_SERVER_ERROR")
                        .message("An unexpected error occurred")
                        .build()
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDTO> handleRuntimeException(RuntimeException ex) {
        logger.error("Exception: ", ex);

        return ResponseEntity.status(400).body(
                ErrorResponseDTO
                        .builder()
                        .code("RUNTIME_EXCEPTION")
                        .message(ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        logger.error("Exception: ", ex);

        return ResponseEntity.status(404).body(
                ErrorResponseDTO
                        .builder()
                        .code("NOT_FOUND")
                        .message("The requested resource was not found")
                        .build()
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.error("Exception: ", ex);

        return ResponseEntity.status(400).body(
                ErrorResponseDTO
                        .builder()
                        .code("ILLEGAL_ARGUMENT")
                        .message(ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadCredentialsException(BadCredentialsException ex) {
        return ResponseEntity.status(401).body(
                ErrorResponseDTO
                        .builder()
                        .code("BAD_CREDENTIALS")
                        .message("Invalid email or password")
                        .build()
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDTO<Map<String, String>>> handleConstraintViolationException(
            ConstraintViolationException e) {
        logger.error("Exception: ", e);

        Map<String, String> errors = new HashMap<>();
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            String propertyPath = violation.getPropertyPath().toString();
            // Extract just the parameter name (e.g., "methodName.parameterName" -> "parameterName")
            String fieldName = propertyPath.contains(".") ?
                    propertyPath.substring(propertyPath.lastIndexOf('.') + 1) : propertyPath;
            errors.put(fieldName, violation.getMessage());
        }

        ErrorResponseDTO<Map<String, String>> errorResponse = ErrorResponseDTO.<Map<String, String>>builder()
                .code("VALIDATION_FAILED")
                .message("Validation failed for one or more fields")
                .data(errors)
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationException(HandlerMethodValidationException e) {
        logger.error("Exception: ", e);


        ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                .code("VALIDATION_FAILED")
                .message("Validation failed for one or more fields")
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO<Map<String, String>>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        logger.error("Exception: ", e);

        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        ErrorResponseDTO<Map<String, String>> errorResponse = ErrorResponseDTO.<Map<String, String>>builder()
                .code("VALIDATION_FAILED")
                .message("Validation failed for one or more fields")
                .data(errors)
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }
}
