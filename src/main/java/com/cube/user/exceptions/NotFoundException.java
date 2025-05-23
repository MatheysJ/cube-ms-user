package com.cube.user.exceptions;

import com.cube.user.dtos.internal.ExceptionCode;

public class NotFoundException extends BusinessException {

    public NotFoundException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
