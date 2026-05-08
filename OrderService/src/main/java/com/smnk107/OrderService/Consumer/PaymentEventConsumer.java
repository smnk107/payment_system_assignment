package com.smnk107.OrderService.Consumer;

import com.smnk107.OrderService.Entities.Orders;
import com.smnk107.PaymentService.DTOs.PaymentEvent;
import com.smnk107.OrderService.Repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentEventConsumer {

    private final OrderRepository orderRepository;

//    @KafkaListener(
//            topics = "payment-event",
//            groupId = "ORDER-SERVICE"
//    )
    @KafkaListener(
            topics = "payment-event",
            groupId = "ORDER-SERVICE",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(PaymentEvent event) {

        Orders order = orderRepository.findById(event.getOrderId())
                .orElseThrow();

        if(event.getPaymentStatus().equals("SUCCESS")) {
            order.setStatus("CONFIRMED");
        }
        else{
            order.setStatus("PAYMENT_FAILED");
        }

        orderRepository.save(order);


    }
}