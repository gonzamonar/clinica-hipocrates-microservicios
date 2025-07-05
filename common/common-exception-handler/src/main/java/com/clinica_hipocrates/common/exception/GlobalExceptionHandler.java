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
    public ResponseEntity<ApiError> handleBadRequest(BadRequestException ex) {
        return ResponseEntity.badRequest()
                .body(new ApiError(ErrorCode.BAD_REQUEST, ex.getMessage()));
    }

    // Spring Validation exceptions handler
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .toList();

        return ResponseEntity.badRequest()
                .body(new ApiError(ErrorCode.VALIDATION_ERROR, errors.toString()));
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<com.clinica_hipocrates.common.exception.ApiError> handleDuplicate(DuplicateResourceException ex) {
        return ResponseEntity.badRequest()
                .body(new ApiError(ErrorCode.DUPLICATE_RESOURCE, ex.getMessage()));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiError> handleUnauthorized(UnauthorizedException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiError(ErrorCode.UNAUTHORIZED, ex.getMessage()));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApiError> handleForbidden(ForbiddenException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ApiError(ErrorCode.FORBIDDEN, ex.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError(ErrorCode.NOT_FOUND, ex.getMessage()));
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
                .body(new ApiError(ErrorCode.DATA_INTEGRITY_VIOLATION, userMessage));
    }

    private String getRootCauseMessage(Throwable ex) {
        Throwable root = ex;
        while (root.getCause() != null) {
            root = root.getCause();
        }
        return root.getMessage();
    }

    @ExceptionHandler(ServerException.class)
    public ResponseEntity<ApiError> handleServerError(ServerException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError(ErrorCode.SERVER_ERROR, ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError(ErrorCode.INTERNAL_ERROR, "An unexpected server error has occurred."));
    }

}