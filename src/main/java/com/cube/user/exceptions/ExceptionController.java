package com.cube.user.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ExceptionResponse> handleBusinessException(BusinessException ex) {

        ExceptionResponse response = ExceptionResponse.builder().code(ex.getCode()).message(ex.getMessage()).build();

        log.error("Exception code");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
