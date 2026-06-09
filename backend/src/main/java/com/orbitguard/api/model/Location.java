package com.orbitguard.api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Representa um local geográfico que o usuário deseja monitorar. Um local pode
 * ser uma cidade, instalação ou qualquer lugar nomeado onde riscos ambientais
 * serão avaliados. As coordenadas são armazenadas como latitude e longitude
 * para integração com APIs climáticas externas.
 */
@Entity
@Data
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String city;
    private String state;
    private Double latitude;
    private Double longitude;

    private Boolean favorite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (this.favorite == null) {
            this.favorite = false;
        }
    }
}
