package com.cube.user.exceptions;

import com.cube.user.models.internal.ExceptionCode;

public class InternalException extends BusinessException {

    public InternalException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }

}
