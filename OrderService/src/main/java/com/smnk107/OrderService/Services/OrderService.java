package com.smnk107.OrderService.Services;

import com.smnk107.OrderService.Consumer.PaymentEventConsumer;
import com.smnk107.OrderService.Entities.OrderStatus;
import com.smnk107.OrderService.Entities.Orders;
import com.smnk107.OrderService.FeignClient.PaymentFeignClient;
import com.smnk107.OrderService.Repositories.OrderRepository;
import com.smnk107.PaymentService.DTOs.PaymentRequest;
import com.smnk107.PaymentService.DTOs.PaymentResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@EnableFeignClients
public class OrderService {

    private final OrderRepository orderRepository;
    private final PaymentFeignClient paymentFeignClient;
    private final PaymentEventConsumer paymentEventConsumer;

    @Transactional
    public String createOrder(Long userId, Double amount) {

        Orders order = Orders.builder()
                .userId(userId)
                .amount(amount)
                .status(OrderStatus.PENDING_PAYMENT.name())
                .createdAt(LocalDateTime.now())
                .build();

        Orders savedOrder = orderRepository.save(order);

//        try {
//
            PaymentRequest request = new PaymentRequest();
            request.setAmount(amount);
            request.setOrderId(order.getId());
//
            PaymentResponse response =
                    paymentFeignClient.initiatePayment(
                            request,
                            UUID.randomUUID().toString()
                    );
//
//            savedOrder.setStatus(OrderStatus.CONFIRMED.name());
//
//        } catch (Exception e) {
//
//            savedOrder.setStatus(OrderStatus.PAYMENT_FAILED.name());
//        }

 //       orderRepository.save(savedOrder);

    return "Order Status : " + savedOrder.getStatus();
    }

    public Orders getOrder(Long id) {

        return orderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));
    }
}
