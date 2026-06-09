package com.orbitguard.api.repository;

import com.orbitguard.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Abstração de repositório para operações CRUD em entidades {@link User}.
 *
 * Estender {@link JpaRepository} fornece métodos genéricos como findAll, save e
 * delete sem exigir código repetitivo. Métodos de consulta customizados podem
 * ser declarados seguindo as convenções de nomenclatura do Spring Data.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
