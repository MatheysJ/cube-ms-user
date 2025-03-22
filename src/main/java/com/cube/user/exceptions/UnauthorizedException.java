package com.cube.user.exceptions;

import com.cube.user.models.internal.ExceptionCode;

public class UnauthorizedException extends BusinessException {
    public UnauthorizedException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
