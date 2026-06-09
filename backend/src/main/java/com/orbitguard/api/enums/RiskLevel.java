package com.orbitguard.api.enums;

/**
 * Representa o nível qualitativo de risco derivado das condições climáticas.
 *
 * Uma enumeração simples facilita a extensão das categorias de risco no futuro
 * sem espalhar strings mágicas pelo código. Os níveis indicam, de forma geral,
 * a urgência com que o usuário deve reagir às condições atuais.
 */
public enum RiskLevel {
    LOW,
    MEDIUM,
    HIGH
}
