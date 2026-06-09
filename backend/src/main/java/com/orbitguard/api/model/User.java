package com.orbitguard.api.model;

import com.orbitguard.api.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Entidade de usuário que representa uma pessoa capaz de se autenticar no sistema.
 *
 * Os campos devem ser mantidos mínimos aqui; qualquer informação adicional de
 * perfil pode ser armazenada em tabelas separadas ou agregada de outros
 * serviços, se necessário. Senhas devem ser armazenadas como valores com hash
 * (não em texto puro) em implantações de produção.
 */
@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (this.role == null) {
            this.role = UserRole.USER;
        }
    }

}
