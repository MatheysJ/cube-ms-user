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
        log.warn("BusinessException thrown with message: [{}]", ex.getMessage());

        ExceptionResponse response = buildExceptionResponse(ex);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundException(NotFoundException ex) {
        log.warn("NotFoundException thrown with message: [{}]", ex.getMessage());

        ExceptionResponse response = buildExceptionResponse(ex);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    private ExceptionResponse buildExceptionResponse(BusinessException ex) {
        return ExceptionResponse.builder().code(ex.getCode()).message(ex.getMessage()).build();
    }
}
