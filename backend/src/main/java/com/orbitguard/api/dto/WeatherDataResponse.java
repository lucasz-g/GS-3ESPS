package com.orbitguard.api.dto;

/**
 * DTO de resposta para dados climáticos obtidos de uma API externa. Este objeto
 * é retornado pelo {@link com.orbitguard.api.client.WeatherClient} e usado
 * internamente pelo serviço de cálculo de risco.
 */
public record WeatherDataResponse(
    Double temperature,
    Double windSpeed,
    Double rainProbability,
    String condition
) {}
