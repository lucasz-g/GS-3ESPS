package com.orbitguard.api.service;

import com.orbitguard.api.client.WeatherClient;
import com.orbitguard.api.dto.RiskReportResponse;
import com.orbitguard.api.dto.WeatherDataResponse;
import com.orbitguard.api.enums.RiskLevel;
import com.orbitguard.api.exception.NotFoundException;
import com.orbitguard.api.model.Location;
import com.orbitguard.api.model.RiskReport;
import com.orbitguard.api.repository.LocationRepository;
import com.orbitguard.api.repository.RiskReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Encapsula a lógica para calcular e persistir relatórios de risco. Este
 * serviço é responsável por chamar APIs climáticas externas e aplicar regras do
 * domínio para derivar níveis de risco e recomendações.
 */
@Service
public class RiskService {

    private final LocationRepository locationRepository;
    private final RiskReportRepository riskReportRepository;
    private final WeatherClient weatherClient;

    public RiskService(LocationRepository locationRepository,
                       RiskReportRepository riskReportRepository,
                       WeatherClient weatherClient) {
        this.locationRepository = locationRepository;
        this.riskReportRepository = riskReportRepository;
        this.weatherClient = weatherClient;
    }

    /**
     * Gera um novo relatório de risco para um local e usuário informados. Busca
     * dados climáticos, calcula o risco, armazena o relatório e retorna um DTO
     * ao chamador.
     *
     * @param locationId id do local a ser monitorado
     * @param userId     id do usuário que solicita o relatório
     * @return um {@link RiskReportResponse} contendo os dados calculados
     */
    public RiskReportResponse generateRiskReport(Long locationId, Long userId) {
        Location location = locationRepository.findByIdAndUserId(locationId, userId)
            .orElseThrow(() -> new NotFoundException("Local não encontrado"));
        WeatherDataResponse data = weatherClient.getWeather(location.getLatitude(), location.getLongitude());
        RiskLevel level = calculateRisk(data);
        String recommendation = generateRecommendation(level);

        RiskReport report = new RiskReport();
        report.setLocation(location);
        report.setTemperature(data.temperature());
        report.setWindSpeed(data.windSpeed());
        report.setRainProbability(data.rainProbability());
        report.setWeatherCondition(data.condition());
        report.setRiskLevel(level);
        report.setRecommendation(recommendation);
        riskReportRepository.save(report);

        return toResponse(report);
    }

    /**
     * Recupera o histórico completo de risco de um usuário, ordenado pelos
     * relatórios mais recentes primeiro.
     *
     * @param userId id do usuário
     * @return lista de {@link RiskReportResponse}
     */
    public List<RiskReportResponse> getHistory(Long userId) {
        return riskReportRepository.findByLocationUserIdOrderByCreatedAtDesc(userId)
            .stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    /**
     * Recupera o histórico de risco de um local específico de um usuário.
     *
     * @param locationId id do local
     * @param userId     id do usuário
     * @return lista de {@link RiskReportResponse}
     */
    public List<RiskReportResponse> getHistoryByLocation(Long locationId, Long userId) {
        // garante que o local existe para o usuário
        locationRepository.findByIdAndUserId(locationId, userId)
            .orElseThrow(() -> new NotFoundException("Local não encontrado"));
        return riskReportRepository.findByLocationIdOrderByCreatedAtDesc(locationId)
            .stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    /**
     * Algoritmo simples de cálculo de risco baseado em métricas climáticas
     * básicas. Modifique este método para incorporar dados adicionais ou uma
     * lógica mais sofisticada conforme necessário.
     */
    private RiskLevel calculateRisk(WeatherDataResponse data) {
        if (data.rainProbability() != null && data.rainProbability() > 70.0
            || data.windSpeed() != null && data.windSpeed() > 40.0
            || data.temperature() != null && (data.temperature() < 0 || data.temperature() > 40)) {
            return RiskLevel.HIGH;
        } else if (data.rainProbability() != null && data.rainProbability() > 30.0
            || data.windSpeed() != null && data.windSpeed() > 20.0
            || data.temperature() != null && (data.temperature() < 5 || data.temperature() > 32)) {
            return RiskLevel.MEDIUM;
        }
        return RiskLevel.LOW;
    }

    /**
     * Gera uma recomendação legível para humanos com base na categoria de risco.
     */
    private String generateRecommendation(RiskLevel level) {
        return switch (level) {
            case HIGH -> "Evite deslocamentos e siga planos de contingência.";
            case MEDIUM -> "Monitorar condições e evitar áreas de risco.";
            case LOW -> "Situação estável. Mantenha monitoramento.";
        };
    }

    /**
     * Mapeia uma entidade {@link RiskReport} para um record de resposta. O nome
     * do local é incluído explicitamente para evitar problemas de carregamento tardio
     * ao serializar fora de uma transação.
     */
    private RiskReportResponse toResponse(RiskReport report) {
        String locationName = report.getLocation() != null ? report.getLocation().getName() : null;
        return new RiskReportResponse(
            report.getId(),
            locationName,
            report.getTemperature(),
            report.getWindSpeed(),
            report.getRainProbability(),
            report.getWeatherCondition(),
            report.getRiskLevel(),
            report.getRecommendation(),
            report.getCreatedAt()
        );
    }
}
