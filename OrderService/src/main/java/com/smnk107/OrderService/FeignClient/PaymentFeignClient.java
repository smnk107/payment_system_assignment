package com.smnk107.OrderService.FeignClient;

import com.smnk107.PaymentService.DTOs.PaymentRequest;
import com.smnk107.PaymentService.DTOs.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "PAYMENT-SERVICE")
public interface PaymentFeignClient {

    @PostMapping("/payments/initiate")
    PaymentResponse initiatePayment(
            @RequestBody PaymentRequest request,
            @RequestHeader("Idempotency-Key") String key
    );
}
