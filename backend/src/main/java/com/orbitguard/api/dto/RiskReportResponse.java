package com.orbitguard.api.dto;

import com.orbitguard.api.enums.RiskLevel;

import java.time.LocalDateTime;

/**
 * Resposta retornada ao gerar ou consultar relatórios de risco. Inclui métricas
 * climáticas brutas e informações de risco derivadas para que o cliente possa
 * exibir um panorama completo ao usuário.
 */
public record RiskReportResponse(
    Long id,
    String locationName,
    Double temperature,
    Double windSpeed,
    Double rainProbability,
    String weatherCondition,
    RiskLevel riskLevel,
    String recommendation,
    LocalDateTime createdAt
) {}
