package com.youth.manito.exception;

public record ExceptionResponse(
    String message
) {
    public static ExceptionResponse of(String message) {
        return new ExceptionResponse(message);
    }
}
