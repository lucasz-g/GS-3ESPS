package com.orbitguard.api.controller;

import com.orbitguard.api.dto.LocationRequest;
import com.orbitguard.api.dto.LocationResponse;
import com.orbitguard.api.service.LocationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador que expõe operações CRUD para locais monitorados. Para
 * simplificar, espera-se que o id do usuário atual seja informado pelo cabeçalho
 * "X-User-Id" da requisição. Em uma aplicação real, esse valor viria do token
 * JWT autenticado.
 */
@RestController
@RequestMapping("/locations")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping
    public ResponseEntity<LocationResponse> create(@Valid @RequestBody LocationRequest request,
                                                   HttpServletRequest httpRequest) {
        Long userId = parseUserId(httpRequest);
        LocationResponse response = locationService.create(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<LocationResponse>> findAll(HttpServletRequest httpRequest) {
        Long userId = parseUserId(httpRequest);
        List<LocationResponse> locations = locationService.findAll(userId);
        return ResponseEntity.ok(locations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationResponse> findById(@PathVariable Long id,
                                                     HttpServletRequest httpRequest) {
        Long userId = parseUserId(httpRequest);
        LocationResponse response = locationService.findById(id, userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocationResponse> update(@PathVariable Long id,
                                                   @Valid @RequestBody LocationRequest request,
                                                   HttpServletRequest httpRequest) {
        Long userId = parseUserId(httpRequest);
        LocationResponse response = locationService.update(id, request, userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       HttpServletRequest httpRequest) {
        Long userId = parseUserId(httpRequest);
        locationService.delete(id, userId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/favorite")
    public ResponseEntity<LocationResponse> toggleFavorite(@PathVariable Long id,
                                                           HttpServletRequest httpRequest) {
        Long userId = parseUserId(httpRequest);
        LocationResponse response = locationService.toggleFavorite(id, userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/favorites")
    public ResponseEntity<List<LocationResponse>> findFavorites(HttpServletRequest httpRequest) {
        Long userId = parseUserId(httpRequest);
        List<LocationResponse> favorites = locationService.findFavorites(userId);
        return ResponseEntity.ok(favorites);
    }

    private Long parseUserId(HttpServletRequest request) {
        String header = request.getHeader("X-User-Id");
        if (header == null || header.isBlank()) {
            // id de usuário padrão para demonstração quando nenhum for informado
            return 1L;
        }
        return Long.valueOf(header);
    }
}
