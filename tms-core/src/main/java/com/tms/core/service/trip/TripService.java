package com.tms.core.service.trip;

import com.tms.api.dto.trip.TripDTO;
import com.tms.core.entity.trip.Trip;
import com.tms.infrastructure.repository.route.RouteRepository;
import com.tms.infrastructure.repository.vehicle.VehicleRepository;
import com.tms.infrastructure.repository.driver.DriverRepository;
import com.tms.infrastructure.repository.trip.TripRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TripService {

    private final TripRepository tripRepository;
    private final RouteRepository routeRepository;
    private final VehicleRepository vehicleRepository;
    private final DriverRepository driverRepository;

    public TripService(TripRepository tripRepository, RouteRepository routeRepository, 
                       VehicleRepository vehicleRepository, DriverRepository driverRepository) {
        this.tripRepository = tripRepository;
        this.routeRepository = routeRepository;
        this.vehicleRepository = vehicleRepository;
        this.driverRepository = driverRepository;
    }

    public TripDTO createTrip(TripDTO tripDTO) {
        Trip trip = new Trip();
        mapToEntity(tripDTO, trip);
        trip = tripRepository.save(trip);
        return mapToDTO(trip);
    }

    public List<TripDTO> getAllTrips() {
        return tripRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public TripDTO getTripById(UUID id) {
        return tripRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Trip not found"));
    }

    public TripDTO updateTrip(UUID id, TripDTO tripDTO) {
        Trip trip = tripRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trip not found"));
        mapToEntity(tripDTO, trip);
        trip = tripRepository.save(trip);
        return mapToDTO(trip);
    }

    public void deleteTrip(UUID id) {
        tripRepository.deleteById(id);
    }

    private TripDTO mapToDTO(Trip trip) {
        TripDTO dto = new TripDTO();
        dto.setId(trip.getId());
        dto.setRouteId(trip.getRoute().getId());
        dto.setVehicleId(trip.getVehicle().getId());
        dto.setDriverId(trip.getDriver().getId());
        dto.setDepartureTime(trip.getDepartureTime());
        dto.setArrivalTime(trip.getArrivalTime());
        dto.setStatus(trip.getStatus());
        return dto;
    }

    private void mapToEntity(TripDTO dto, Trip trip) {
        trip.setRoute(routeRepository.findById(dto.getRouteId()).orElseThrow());
        trip.setVehicle(vehicleRepository.findById(dto.getVehicleId()).orElseThrow());
        trip.setDriver(driverRepository.findById(dto.getDriverId()).orElseThrow());
        trip.setDepartureTime(dto.getDepartureTime());
        trip.setArrivalTime(dto.getArrivalTime());
        trip.setStatus(dto.getStatus());
    }
}
