package com.orbitguard.api.service;

import com.orbitguard.api.dto.LocationRequest;
import com.orbitguard.api.dto.LocationResponse;
import com.orbitguard.api.exception.NotFoundException;
import com.orbitguard.api.model.Location;
import com.orbitguard.api.model.User;
import com.orbitguard.api.repository.LocationRepository;
import com.orbitguard.api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço que encapsula a lógica de negócio dos locais monitorados. Garante
 * verificações de propriedade e fornece mapeamento entre entidades e DTOs.
 */
@Service
public class LocationService {

    private final LocationRepository locationRepository;
    private final UserRepository userRepository;

    public LocationService(LocationRepository locationRepository,
                           UserRepository userRepository) {
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
    }

    public LocationResponse create(LocationRequest request, Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        Location location = new Location();
        location.setName(request.name());
        location.setCity(request.city());
        location.setState(request.state());
        location.setLatitude(request.latitude());
        location.setLongitude(request.longitude());
        location.setUser(user);
        locationRepository.save(location);
        return toResponse(location);
    }

    public List<LocationResponse> findAll(Long userId) {
        return locationRepository.findByUserId(userId)
            .stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public LocationResponse findById(Long id, Long userId) {
        Location location = locationRepository.findByIdAndUserId(id, userId)
            .orElseThrow(() -> new NotFoundException("Local não encontrado"));
        return toResponse(location);
    }

    public LocationResponse update(Long id, LocationRequest request, Long userId) {
        Location location = locationRepository.findByIdAndUserId(id, userId)
            .orElseThrow(() -> new NotFoundException("Local não encontrado"));
        location.setName(request.name());
        location.setCity(request.city());
        location.setState(request.state());
        location.setLatitude(request.latitude());
        location.setLongitude(request.longitude());
        locationRepository.save(location);
        return toResponse(location);
    }

    public void delete(Long id, Long userId) {
        Location location = locationRepository.findByIdAndUserId(id, userId)
            .orElseThrow(() -> new NotFoundException("Local não encontrado"));
        locationRepository.delete(location);
    }

    public LocationResponse toggleFavorite(Long id, Long userId) {
        Location location = locationRepository.findByIdAndUserId(id, userId)
            .orElseThrow(() -> new NotFoundException("Local não encontrado"));
        location.setFavorite(!Boolean.TRUE.equals(location.getFavorite()));
        locationRepository.save(location);
        return toResponse(location);
    }

    public List<LocationResponse> findFavorites(Long userId) {
        return locationRepository.findByUserIdAndFavoriteTrue(userId)
            .stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    private LocationResponse toResponse(Location location) {
        return new LocationResponse(
            location.getId(),
            location.getName(),
            location.getCity(),
            location.getState(),
            location.getLatitude(),
            location.getLongitude(),
            location.getFavorite()
        );
    }
}
