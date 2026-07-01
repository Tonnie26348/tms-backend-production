package com.tms.core.dto.payment;

import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PaymentDTO {
    private UUID id;
    private UUID bookingId;
    private BigDecimal amount;
    private String currency;
    private String paymentMethod;
    private String status;
    private String externalTransactionId;
}

