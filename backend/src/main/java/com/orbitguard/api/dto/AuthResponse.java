package com.orbitguard.api.dto;

/**
 * Resposta com informações retornadas após autenticação ou cadastro bem-sucedido.
 * Além do token JWT, inclui dados básicos de perfil que podem ser exibidos no
 * aplicativo móvel. Os campos podem ser estendidos conforme necessário.
 */
public record AuthResponse(
    String token,
    String name,
    String email
) {}
