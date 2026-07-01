package com.tms.core.service.payment.strategy;

import com.tms.core.dto.payment.PaymentDTO;
import org.springframework.stereotype.Component;

@Component
public class MpesaPaymentStrategy implements PaymentStrategy {

    @Override
    public void processPayment(PaymentDTO paymentDTO) {
        // TODO: Implement M-Pesa Daraja API integration here
        // 1. Authenticate with Daraja
        // 2. Initiate STK Push (or C2B)
        // 3. Handle callback
        System.out.println("Processing M-Pesa payment for booking: " + paymentDTO.getBookingId());
    }

    @Override
    public String getPaymentMethod() {
        return "M-PESA";
    }
}


