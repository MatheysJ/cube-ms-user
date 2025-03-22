package com.cube.user.exceptions;

import com.cube.user.models.internal.ExceptionCode;

public class NotFoundException extends BusinessException {

    public NotFoundException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
