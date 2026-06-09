package com.orbitguard.api.dto;

/**
 * Representação serializada de um local retornada aos clientes. Expõe apenas os
 * campos necessários, ocultando os detalhes de implementação da entidade.
 */
public record LocationResponse(
    Long id,
    String name,
    String city,
    String state,
    Double latitude,
    Double longitude,
    Boolean favorite
) {}
