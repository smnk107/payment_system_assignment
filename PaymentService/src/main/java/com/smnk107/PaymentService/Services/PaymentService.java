package com.smnk107.PaymentService.Services;

import com.smnk107.PaymentService.DTOs.PaymentRequest;
import com.smnk107.PaymentService.DTOs.PaymentResponse;
import com.smnk107.PaymentService.Entities.IdempotencyKey;
import com.smnk107.PaymentService.Entities.Payment;
import com.smnk107.PaymentService.DTOs.PaymentStatus;
import com.smnk107.PaymentService.DTOs.PaymentEvent;
import com.smnk107.PaymentService.Exceptions.PaymentNotFoundException;
import com.smnk107.PaymentService.Producer.PaymentProducer;
import com.smnk107.PaymentService.Repositories.IdempotencyRepository;
import com.smnk107.PaymentService.Repositories.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final IdempotencyRepository idempotencyRepository;
    private final PaymentProducer paymentProducer;

    @Transactional
    public PaymentResponse initiatePayment(PaymentRequest request, String key) {

        // 1. Check idempotency
        Optional<IdempotencyKey> existingKey = idempotencyRepository.findByIdempotencyKey(key);

        if (existingKey.isPresent()) {
            Payment payment = paymentRepository.findById(existingKey.get().getPaymentId())
                    .orElseThrow(() -> new RuntimeException("Payment not found"));
            return mapToResponse(payment);
        }

        // 2. Create new payment
        Payment payment = Payment.builder()
                .amount(request.getAmount())
                .status(PaymentStatus.SUCCESS) // simulate success
                .idempotencyKey(key)
                .createdAt(LocalDateTime.now())
                .build();

        Payment savedPayment = paymentRepository.save(payment);

        // 3. Save idempotency key
        IdempotencyKey idempotencyKey = IdempotencyKey.builder()
                .idempotencyKey(key)
                .paymentId(savedPayment.getId())
                .build();

        idempotencyRepository.save(idempotencyKey);

        System.out.println("Payment done---");
        PaymentEvent paymentEvent = PaymentEvent.builder()
                        .paymentId(savedPayment.getId())
                                .paymentStatus("SUCCESS")
                                        .orderId(request.getOrderId())
                                            .build();
        paymentProducer.publish(
                paymentEvent
        );

        return mapToResponse(savedPayment);
    }

    public PaymentResponse getPayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found"));

        return mapToResponse(payment);
    }

    private PaymentResponse mapToResponse(Payment payment) {
        return PaymentResponse.builder()
                .paymentId(payment.getId())
                .amount(payment.getAmount())
                .status(payment.getStatus())
                .build();
    }
}
