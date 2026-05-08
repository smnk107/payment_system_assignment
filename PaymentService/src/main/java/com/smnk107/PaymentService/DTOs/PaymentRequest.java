package com.smnk107.PaymentService.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {
    private Long orderId;
    private Double amount;
}
