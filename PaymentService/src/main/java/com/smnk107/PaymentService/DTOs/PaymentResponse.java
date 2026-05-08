package com.smnk107.PaymentService.DTOs;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PaymentResponse {
    private Long paymentId;
    private Double amount;
    private PaymentStatus status;
}
