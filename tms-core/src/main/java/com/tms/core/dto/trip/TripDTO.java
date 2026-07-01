package com.tms.core.dto.trip;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TripDTO {
    private UUID id;
    private UUID routeId;
    private UUID vehicleId;
    private UUID driverId;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String status;
}

