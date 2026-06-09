package com.orbitguard.api.exception;

/**
 * Exceção lançada quando uma regra de negócio é violada. Controladores podem
 * capturar esta exceção e traduzi-la para um status HTTP adequado (ex.: 400 Bad
 * Request). Usar um tipo específico de exceção separa erros de negócio de
 * falhas técnicas.
 */
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
