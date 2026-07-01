package com.tms.service;

import com.tms.dto.TripDto;
import com.tms.entity.Trip;
import com.tms.exception.ResourceNotFoundException;
import com.tms.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TripService {
    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private RouteRepository routeRepository;
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private VehicleRepository vehicleRepository;

    public List<TripDto> getAllTrips() {
        return tripRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public TripDto createTrip(TripDto tripDto) {
        Trip trip = Trip.builder()
                .route(routeRepository.findById(tripDto.getRouteId()).orElseThrow(() -> new ResourceNotFoundException("Route not found")))
                .driver(driverRepository.findById(tripDto.getDriverId()).orElseThrow(() -> new ResourceNotFoundException("Driver not found")))
                .vehicle(vehicleRepository.findById(tripDto.getVehicleId()).orElseThrow(() -> new ResourceNotFoundException("Vehicle not found")))
                .departureTime(tripDto.getDepartureTime())
                .arrivalTime(tripDto.getArrivalTime())
                .status(tripDto.getStatus())
                .price(tripDto.getPrice())
                .recurring(tripDto.getRecurring())
                .recurrencePattern(tripDto.getRecurrencePattern())
                .build();
        trip = tripRepository.save(trip);
        return mapToDto(trip);
    }

    private TripDto mapToDto(Trip trip) {
        return TripDto.builder()
                .id(trip.getId())
                .routeId(trip.getRoute().getId())
                .driverId(trip.getDriver().getId())
                .vehicleId(trip.getVehicle().getId())
                .departureTime(trip.getDepartureTime())
                .arrivalTime(trip.getArrivalTime())
                .status(trip.getStatus())
                .price(trip.getPrice())
                .recurring(trip.getRecurring())
                .recurrencePattern(trip.getRecurrencePattern())
                .build();
    }
}
