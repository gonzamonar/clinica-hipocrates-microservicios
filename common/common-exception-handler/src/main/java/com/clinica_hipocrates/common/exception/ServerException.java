package com.clinica_hipocrates.common.exception;

public class ServerException extends RuntimeException {
    public ServerException(String message) {
        super(message);
    }
}