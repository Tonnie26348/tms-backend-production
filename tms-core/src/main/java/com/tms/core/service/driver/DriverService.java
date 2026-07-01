package com.tms.core.service.driver;

import com.tms.core.dto.driver.DriverDTO;
import com.tms.core.entity.driver.Driver;
import com.tms.infrastructure.repository.driver.DriverRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DriverService {

    private final DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public DriverDTO createDriver(DriverDTO driverDTO) {
        Driver driver = new Driver();
        mapToEntity(driverDTO, driver);
        driver = driverRepository.save(driver);
        return mapToDTO(driver);
    }

    public List<DriverDTO> getAllDrivers() {
        return driverRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public DriverDTO getDriverById(UUID id) {
        return driverRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Driver not found"));
    }

    public DriverDTO updateDriver(UUID id, DriverDTO driverDTO) {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Driver not found"));
        mapToEntity(driverDTO, driver);
        driver = driverRepository.save(driver);
        return mapToDTO(driver);
    }

    public void deleteDriver(UUID id) {
        driverRepository.deleteById(id);
    }

    private DriverDTO mapToDTO(Driver driver) {
        DriverDTO dto = new DriverDTO();
        dto.setId(driver.getId());
        dto.setFullName(driver.getFullName());
        dto.setLicenseNumber(driver.getLicenseNumber());
        dto.setLicenseExpiryDate(driver.getLicenseExpiryDate());
        dto.setPhoneNumber(driver.getPhoneNumber());
        dto.setStatus(driver.getStatus());
        return dto;
    }

    private void mapToEntity(DriverDTO dto, Driver driver) {
        driver.setFullName(dto.getFullName());
        driver.setLicenseNumber(dto.getLicenseNumber());
        driver.setLicenseExpiryDate(dto.getLicenseExpiryDate());
        driver.setPhoneNumber(dto.getPhoneNumber());
        driver.setStatus(dto.getStatus());
    }
}


