package com.example.restapi.exceptions;

import com.example.restapi.io.ErrorObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

/**
 * Global exceptional handler for all the exceptions
 * @author boroboro01
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorObject handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.error("Throwing the ResuorceNotFoundException from GlobalExceptionHandler {}", ex.getMessage());
        return ErrorObject.builder()
                .errorCode("DATA_NOT_FOUND")
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .timestamp(new Date())
                .build();
    }
}
