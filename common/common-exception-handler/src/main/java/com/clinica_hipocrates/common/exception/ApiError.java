package com.clinica_hipocrates.common.exception;

public record ApiError(ErrorCode code, String message) {}