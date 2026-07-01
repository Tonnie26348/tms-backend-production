package com.tms.core.dto.driver;

import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class DriverDTO {
    private UUID id;
    private String fullName;
    private String licenseNumber;
    private LocalDate licenseExpiryDate;
    private String phoneNumber;
    private String status;
}

