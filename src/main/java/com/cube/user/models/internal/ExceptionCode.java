package com.cube.user.models.internal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionCode {
    ALREADY_EXISTS("Usuário com o e-mail informado já existe");

    private final String message;
}
