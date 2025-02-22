package com.youth.manito.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BadRequestException extends RuntimeException {
    private final String message;
}
