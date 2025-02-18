package com.youth.manito.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        log.warn("EntityNotFoundException 발생: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(ExceptionResponse.of(e.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequestException(BadRequestException e) {
        log.warn("BadRequestException 발생: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(ExceptionResponse.of(e.getMessage()));
    }
}
