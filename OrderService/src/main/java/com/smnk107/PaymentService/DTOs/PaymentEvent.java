package com.smnk107.PaymentService.DTOs;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentEvent {

    private Long orderId;
    private Long paymentId;
    private String paymentStatus;
}