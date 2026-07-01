package com.tms.api.dto.vehicle;

import lombok.Data;
import java.util.UUID;

@Data
public class VehicleDTO {
    private UUID id;
    private String plateNumber;
    private String model;
    private Integer capacity;
    private String status;
}
