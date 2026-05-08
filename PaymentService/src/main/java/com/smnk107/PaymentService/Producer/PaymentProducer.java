package com.smnk107.PaymentService.Producer;

import com.smnk107.PaymentService.DTOs.PaymentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentProducer {

    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    public void publish(PaymentEvent event) {

        kafkaTemplate.send(
                "payment-event",
                event
        );
    }
}
