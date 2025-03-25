package com.cube.user.exceptions;

import com.cube.user.models.internal.ExceptionCode;

public class BadRequestException extends BusinessException {

    public BadRequestException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
