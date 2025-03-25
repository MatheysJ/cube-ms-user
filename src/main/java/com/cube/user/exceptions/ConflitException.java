package com.cube.user.exceptions;

import com.cube.user.models.internal.ExceptionCode;

public class ConflitException extends BusinessException {

    public ConflitException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
