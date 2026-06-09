package com.orbitguard.api.dto;

/**
 * Corpo usado ao criar ou atualizar um local monitorado. As coordenadas devem
 * ser informadas para permitir a consulta de informações climáticas do ponto
 * específico. O userId não é incluído aqui; a camada de serviço deve associar
 * o usuário autenticado.
 */
public record LocationRequest(
    String name,
    String city,
    String state,
    Double latitude,
    Double longitude
) {}
