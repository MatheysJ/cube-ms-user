package com.cube.user.dtos.internal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionCode {
    ALREADY_EXISTS("Usuário com o e-mail informado já existe"),
    INVALID_CREDENTIALS("Credenciais não batem"),
    USER_WITH_ID_DOESNT_EXIST("Usuário com id informado não existe"),
    FAILED_TO_GENERATE_TOKEN("Erro ao gerar token de autenticação"),
    INVALID_TOKEN("O token informado não é válido");

    private final String message;
}
