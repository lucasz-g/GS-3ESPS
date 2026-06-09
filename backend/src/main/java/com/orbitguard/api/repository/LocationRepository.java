package com.orbitguard.api.repository;

import com.orbitguard.api.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório para consultar entidades {@link Location}. Métodos adicionais de
 * consulta permitem buscar locais pertencentes a um usuário específico ou
 * filtrar favoritos.
 */
public interface LocationRepository extends JpaRepository<Location, Long> {

    List<Location> findByUserId(Long userId);

    List<Location> findByUserIdAndFavoriteTrue(Long userId);

    Optional<Location> findByIdAndUserId(Long id, Long userId);
}
