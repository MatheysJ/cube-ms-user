package com.cube.user.exceptions;

import com.cube.user.dtos.internal.ExceptionCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final String code;

    public BusinessException (ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());

        this.code = exceptionCode.name();
    }

}
