package com.tms.service;

import com.tms.dto.VehicleDto;
import com.tms.entity.Vehicle;
import com.tms.exception.ResourceNotFoundException;
import com.tms.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;

    public List<VehicleDto> getAllVehicles() {
        return vehicleRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public VehicleDto getVehicleById(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));
        return mapToDto(vehicle);
    }

    public VehicleDto createVehicle(VehicleDto vehicleDto) {
        Vehicle vehicle = mapToEntity(vehicleDto);
        vehicle = vehicleRepository.save(vehicle);
        return mapToDto(vehicle);
    }

    private VehicleDto mapToDto(Vehicle vehicle) {
        return VehicleDto.builder()
                .id(vehicle.getId())
                .registrationNumber(vehicle.getRegistrationNumber())
                .make(vehicle.getMake())
                .model(vehicle.getModel())
                .year(vehicle.getYear())
                .capacity(vehicle.getCapacity())
                .status(vehicle.getStatus())
                .insurancePolicyNumber(vehicle.getInsurancePolicyNumber())
                .insuranceExpiry(vehicle.getInsuranceExpiry())
                .inspectionExpiry(vehicle.getInspectionExpiry())
                .roadLicenseExpiry(vehicle.getRoadLicenseExpiry())
                .fuelType(vehicle.getFuelType())
                .build();
    }

    private Vehicle mapToEntity(VehicleDto dto) {
        return Vehicle.builder()
                .registrationNumber(dto.getRegistrationNumber())
                .make(dto.getMake())
                .model(dto.getModel())
                .year(dto.getYear())
                .capacity(dto.getCapacity())
                .status(dto.getStatus())
                .insurancePolicyNumber(dto.getInsurancePolicyNumber())
                .insuranceExpiry(dto.getInsuranceExpiry())
                .inspectionExpiry(dto.getInspectionExpiry())
                .roadLicenseExpiry(dto.getRoadLicenseExpiry())
                .fuelType(dto.getFuelType())
                .build();
    }
}
