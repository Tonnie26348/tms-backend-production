package com.tms.core.service.payment;

import com.tms.core.dto.payment.PaymentDTO;
import com.tms.core.entity.payment.Payment;
import com.tms.core.service.payment.strategy.PaymentStrategyFactory;
import com.tms.core.repository.booking.BookingRepository;
import com.tms.core.repository.payment.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final PaymentStrategyFactory paymentStrategyFactory;

    public PaymentService(PaymentRepository paymentRepository, BookingRepository bookingRepository, 
                          PaymentStrategyFactory paymentStrategyFactory) {
        this.paymentRepository = paymentRepository;
        this.bookingRepository = bookingRepository;
        this.paymentStrategyFactory = paymentStrategyFactory;
    }

    public PaymentDTO createPayment(PaymentDTO paymentDTO) {
        // Delegate to specific strategy
        paymentStrategyFactory.getStrategy(paymentDTO.getPaymentMethod()).processPayment(paymentDTO);

        Payment payment = new Payment();
        mapToEntity(paymentDTO, payment);
        payment = paymentRepository.save(payment);
        return mapToDTO(payment);
    }
    
    // ... remaining CRUD methods mapToDTO, mapToEntity unchanged ...

    public List<PaymentDTO> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public PaymentDTO getPaymentById(UUID id) {
        return paymentRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    private PaymentDTO mapToDTO(Payment payment) {
        PaymentDTO dto = new PaymentDTO();
        dto.setId(payment.getId());
        dto.setBookingId(payment.getBooking().getId());
        dto.setAmount(payment.getAmount());
        dto.setCurrency(payment.getCurrency());
        dto.setPaymentMethod(payment.getPaymentMethod());
        dto.setStatus(payment.getStatus());
        dto.setExternalTransactionId(payment.getExternalTransactionId());
        return dto;
    }

    private void mapToEntity(PaymentDTO dto, Payment payment) {
        payment.setBooking(bookingRepository.findById(dto.getBookingId()).orElseThrow());
        payment.setAmount(dto.getAmount());
        payment.setCurrency(dto.getCurrency());
        payment.setPaymentMethod(dto.getPaymentMethod());
        payment.setStatus(dto.getStatus());
        payment.setExternalTransactionId(dto.getExternalTransactionId());
    }
}


