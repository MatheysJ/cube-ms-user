package com.cube.user.exceptions;

import com.cube.user.dtos.internal.ExceptionCode;

public class InternalException extends BusinessException {

    public InternalException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }

}
