package com.tms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleDto {
    private Long id;
    private String registrationNumber;
    private String make;
    private String model;
    private Integer year;
    private Integer capacity;
    private String status;
    private String insurancePolicyNumber;
    private LocalDate insuranceExpiry;
    private LocalDate inspectionExpiry;
    private LocalDate roadLicenseExpiry;
    private String fuelType;
}
