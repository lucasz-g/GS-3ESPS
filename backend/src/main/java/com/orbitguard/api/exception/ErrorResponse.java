package com.orbitguard.api.exception;

import java.time.LocalDateTime;

/**
 * Corpo estruturado retornado quando uma exceção é tratada. Incluir data/hora e
 * código de status HTTP ajuda os clientes a correlacionar erros e implementar
 * novas tentativas ou fluxos de retorno ao usuário.
 */
public record ErrorResponse(
    String message,
    String error,
    int status,
    LocalDateTime timestamp
) {}
