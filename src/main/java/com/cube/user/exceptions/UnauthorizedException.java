package com.cube.user.exceptions;

import com.cube.user.dtos.internal.ExceptionCode;

public class UnauthorizedException extends BusinessException {
    public UnauthorizedException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
