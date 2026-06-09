package com.orbitguard.api.exception;

/**
 * Exceção lançada quando um recurso solicitado não é encontrado. O tratador
 * global de exceções converte isso em uma resposta HTTP 404 Not Found.
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
