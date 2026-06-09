package com.orbitguard.api.model;

import com.orbitguard.api.enums.RiskLevel;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Registra um retrato do risco ambiental calculado para um local específico.
 *
 * Armazenar relatórios históricos permite ao sistema mostrar tendências,
 * fornecer acesso sem conexão a dados calculados anteriormente e servir como
 * registro de auditoria das condições passadas. Cada relatório é vinculado ao local de
 * origem.
 */
@Entity
@Data
@Table(name = "risk_reports")
public class RiskReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double temperature;
    private Double windSpeed;
    private Double rainProbability;
    private String weatherCondition;

    @Enumerated(EnumType.STRING)
    private RiskLevel riskLevel;

    private String recommendation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
