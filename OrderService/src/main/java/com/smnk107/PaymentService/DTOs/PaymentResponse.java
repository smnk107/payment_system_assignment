package com.smnk107.PaymentService.DTOs;

import lombok.*;

@Builder
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private Long paymentId;
    private Double amount;
    private PaymentStatus status;
}
