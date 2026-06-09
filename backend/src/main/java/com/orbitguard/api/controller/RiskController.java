package com.orbitguard.api.controller;

import com.orbitguard.api.dto.RiskReportResponse;
import com.orbitguard.api.service.RiskService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador que expõe pontos de acesso para gerar e consultar relatórios de risco.
 * O id do usuário atual é lido do cabeçalho "X-User-Id" para fins de
 * demonstração. Em um sistema de produção, ele viria do token JWT autenticado.
 */
@RestController
@RequestMapping("/risk")
public class RiskController {

    private final RiskService riskService;

    public RiskController(RiskService riskService) {
        this.riskService = riskService;
    }

    @GetMapping("/{locationId}")
    public ResponseEntity<RiskReportResponse> generateRiskReport(@PathVariable Long locationId,
                                                                HttpServletRequest httpRequest) {
        Long userId = parseUserId(httpRequest);
        RiskReportResponse response = riskService.generateRiskReport(locationId, userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/history")
    public ResponseEntity<List<RiskReportResponse>> getHistory(HttpServletRequest httpRequest) {
        Long userId = parseUserId(httpRequest);
        List<RiskReportResponse> history = riskService.getHistory(userId);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/history/location/{locationId}")
    public ResponseEntity<List<RiskReportResponse>> getHistoryByLocation(@PathVariable Long locationId,
                                                                        HttpServletRequest httpRequest) {
        Long userId = parseUserId(httpRequest);
        List<RiskReportResponse> history = riskService.getHistoryByLocation(locationId, userId);
        return ResponseEntity.ok(history);
    }

    private Long parseUserId(HttpServletRequest request) {
        String header = request.getHeader("X-User-Id");
        if (header == null || header.isBlank()) {
            return 1L;
        }
        return Long.valueOf(header);
    }
}
