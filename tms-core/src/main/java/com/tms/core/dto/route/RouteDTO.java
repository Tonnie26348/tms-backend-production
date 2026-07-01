package com.tms.core.dto.route;

import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class RouteDTO {
    private UUID id;
    private String origin;
    private String destination;
    private Double distanceKm;
    private Integer estimatedDurationMinutes;
    private BigDecimal baseFare;
}

