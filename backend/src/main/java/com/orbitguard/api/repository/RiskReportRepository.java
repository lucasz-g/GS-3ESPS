package com.orbitguard.api.repository;

import com.orbitguard.api.model.RiskReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositório para acessar relatórios históricos de risco. Os métodos definidos
 * aqui permitem ordenar os resultados por data de criação para apresentar as
 * informações mais recentes primeiro.
 */
public interface RiskReportRepository extends JpaRepository<RiskReport, Long> {

    List<RiskReport> findByLocationUserIdOrderByCreatedAtDesc(Long userId);

    List<RiskReport> findByLocationIdOrderByCreatedAtDesc(Long locationId);
}
