package com.tms.core.dto.booking;

import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class BookingDTO {
    private UUID id;
    private UUID userId;
    private UUID tripId;
    private String seatNumber;
    private BigDecimal amountPaid;
    private String status;
}

