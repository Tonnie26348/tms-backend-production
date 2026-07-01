package com.tms.core.service.payment.strategy;

import com.tms.api.dto.payment.PaymentDTO;

public interface PaymentStrategy {
    void processPayment(PaymentDTO paymentDTO);
    String getPaymentMethod();
}
