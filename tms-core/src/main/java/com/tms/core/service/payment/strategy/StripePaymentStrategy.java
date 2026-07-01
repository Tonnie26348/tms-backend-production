package com.tms.core.service.payment.strategy;

import com.tms.core.dto.payment.PaymentDTO;
import org.springframework.stereotype.Component;

@Component
public class StripePaymentStrategy implements PaymentStrategy {

    @Override
    public void processPayment(PaymentDTO paymentDTO) {
        // TODO: Implement Stripe API integration here
        // 1. Initialize Stripe Client
        // 2. Create PaymentIntent
        // 3. Handle response
        System.out.println("Processing Stripe payment for booking: " + paymentDTO.getBookingId());
    }

    @Override
    public String getPaymentMethod() {
        return "STRIPE";
    }
}


