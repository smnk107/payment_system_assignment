package com.smnk107.PaymentService.Controllers;

import com.smnk107.PaymentService.DTOs.PaymentRequest;
import com.smnk107.PaymentService.DTOs.PaymentResponse;
import com.smnk107.PaymentService.Services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/initiate")
    public ResponseEntity<PaymentResponse> initiatePayment(
            @RequestBody PaymentRequest request,
            @RequestHeader("Idempotency-Key") String key) {

        return ResponseEntity.ok(paymentService.initiatePayment(request, key));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPayment(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getPayment(id));
    }
}
