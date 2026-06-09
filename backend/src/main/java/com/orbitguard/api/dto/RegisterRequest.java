package com.orbitguard.api.dto;

/**
 * Corpo para cadastro de novo usuário. Usa a sintaxe de record do Java para
 * gerar automaticamente construtor, getters e equals/hashCode.
 */
public record RegisterRequest(
    String name,
    String email,
    String password
) {}
