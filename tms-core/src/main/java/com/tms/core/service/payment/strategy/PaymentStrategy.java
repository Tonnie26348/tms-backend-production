package com.tms.core.service.payment.strategy;

import com.tms.core.dto.payment.PaymentDTO;

public interface PaymentStrategy {
    void processPayment(PaymentDTO paymentDTO);
    String getPaymentMethod();
}


