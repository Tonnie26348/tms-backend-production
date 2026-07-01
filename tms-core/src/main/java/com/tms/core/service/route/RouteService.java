package com.tms.core.service.route;

import com.tms.api.dto.route.RouteDTO;
import com.tms.core.entity.route.Route;
import com.tms.infrastructure.repository.route.RouteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RouteService {

    private final RouteRepository routeRepository;

    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public RouteDTO createRoute(RouteDTO routeDTO) {
        Route route = new Route();
        mapToEntity(routeDTO, route);
        route = routeRepository.save(route);
        return mapToDTO(route);
    }

    public List<RouteDTO> getAllRoutes() {
        return routeRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public RouteDTO getRouteById(UUID id) {
        return routeRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Route not found"));
    }

    public RouteDTO updateRoute(UUID id, RouteDTO routeDTO) {
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Route not found"));
        mapToEntity(routeDTO, route);
        route = routeRepository.save(route);
        return mapToDTO(route);
    }

    public void deleteRoute(UUID id) {
        routeRepository.deleteById(id);
    }

    private RouteDTO mapToDTO(Route route) {
        RouteDTO dto = new RouteDTO();
        dto.setId(route.getId());
        dto.setOrigin(route.getOrigin());
        dto.setDestination(route.getDestination());
        dto.setDistanceKm(route.getDistanceKm());
        dto.setEstimatedDurationMinutes(route.getEstimatedDurationMinutes());
        dto.setBaseFare(route.getBaseFare());
        return dto;
    }

    private void mapToEntity(RouteDTO dto, Route route) {
        route.setOrigin(dto.getOrigin());
        route.setDestination(dto.getDestination());
        route.setDistanceKm(dto.getDistanceKm());
        route.setEstimatedDurationMinutes(dto.getEstimatedDurationMinutes());
        route.setBaseFare(dto.getBaseFare());
    }
}
