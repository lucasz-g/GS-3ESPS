package com.orbitguard.api.dto;

/**
 * Corpo usado para autenticar um usuário existente.
 */
public record LoginRequest(
    String email,
    String password
) {}
