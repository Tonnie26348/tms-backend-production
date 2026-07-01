package com.tms.core.service.vehicle;

import com.tms.core.dto.vehicle.VehicleDTO;
import com.tms.core.entity.vehicle.Vehicle;
import com.tms.core.repository.vehicle.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public VehicleDTO createVehicle(VehicleDTO vehicleDTO) {
        Vehicle vehicle = new Vehicle();
        mapToEntity(vehicleDTO, vehicle);
        vehicle = vehicleRepository.save(vehicle);
        return mapToDTO(vehicle);
    }

    public List<VehicleDTO> getAllVehicles() {
        return vehicleRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public VehicleDTO getVehicleById(UUID id) {
        return vehicleRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
    }

    public VehicleDTO updateVehicle(UUID id, VehicleDTO vehicleDTO) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        mapToEntity(vehicleDTO, vehicle);
        vehicle = vehicleRepository.save(vehicle);
        return mapToDTO(vehicle);
    }

    public void deleteVehicle(UUID id) {
        vehicleRepository.deleteById(id);
    }

    private VehicleDTO mapToDTO(Vehicle vehicle) {
        VehicleDTO dto = new VehicleDTO();
        dto.setId(vehicle.getId());
        dto.setPlateNumber(vehicle.getPlateNumber());
        dto.setModel(vehicle.getModel());
        dto.setCapacity(vehicle.getCapacity());
        dto.setStatus(vehicle.getStatus());
        return dto;
    }

    private void mapToEntity(VehicleDTO dto, Vehicle vehicle) {
        vehicle.setPlateNumber(dto.getPlateNumber());
        vehicle.setModel(dto.getModel());
        vehicle.setCapacity(dto.getCapacity());
        vehicle.setStatus(dto.getStatus());
    }
}


