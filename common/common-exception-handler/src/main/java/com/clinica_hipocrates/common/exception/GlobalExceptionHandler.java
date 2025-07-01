package com.clinica_hipocrates.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<com.clinica_hipocrates.common.exception.ApiError> handleDuplicate(BadRequestException ex) {
        return ResponseEntity.badRequest()
                .body(new ApiError("400:BAD_REQUEST", ex.getMessage()));
    }

    // Spring Validation exceptions handler
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .toList();

        return ResponseEntity.badRequest()
                .body(new ApiError("400:VALIDATION_ERROR", errors.toString()));
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<com.clinica_hipocrates.common.exception.ApiError> handleDuplicate(DuplicateResourceException ex) {
        return ResponseEntity.badRequest()
                .body(new ApiError("400:DUPLICATE_RESOURCE", ex.getMessage()));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiError> handleNotFound(UnauthorizedException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError("401:UNAUTHORIZED", ex.getMessage()));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApiError> handleNotFound(ForbiddenException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError("403:FORBIDDEN", ex.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError("404:NOT_FOUND", ex.getMessage()));
    }

    // Spring JPA SQL constraint exceptions handler
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String rootMessage = getRootCauseMessage(ex);
        String userMessage;
        if (rootMessage.contains("foreign key constraint")) {
            userMessage = "Cannot delete this resource as it is still being used.";
        } else if (rootMessage.contains("duplicate") || rootMessage.contains("Duplicate entry")) {
            userMessage = "This value must be unique and already exists.";
        } else {
            userMessage = "A data integrity violation occurred.";
        }
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiError("409:DATA_INTEGRITY_VIOLATION", userMessage));
    }

    private String getRootCauseMessage(Throwable ex) {
        Throwable root = ex;
        while (root.getCause() != null) {
            root = root.getCause();
        }
        return root.getMessage();
    }

    @ExceptionHandler(ServerException.class)
    public ResponseEntity<ApiError> handleNotFound(ServerException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError("500:SERVER_ERROR", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError("500:INTERNAL_ERROR", "An unexpected server error has occurred."));
    }

}