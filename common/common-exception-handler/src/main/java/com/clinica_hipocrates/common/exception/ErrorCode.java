package com.clinica_hipocrates.common.exception;

public enum ErrorCode {
    BAD_REQUEST("400:BAD_REQUEST"),
    VALIDATION_ERROR("400:VALIDATION_ERROR"),
    DUPLICATE_RESOURCE("400:DUPLICATE_RESOURCE"),
    UNAUTHORIZED("401:UNAUTHORIZED"),
    FORBIDDEN("403:FORBIDDEN"),
    NOT_FOUND("404:NOT_FOUND"),
    DATA_INTEGRITY_VIOLATION("409:DATA_INTEGRITY_VIOLATION"),
    SERVER_ERROR("500:SERVER_ERROR"),
    INTERNAL_ERROR("INTERNAL_ERROR");

    private final String code;

    ErrorCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
