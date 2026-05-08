package com.smnk107.OrderService.Controllers;

import com.smnk107.OrderService.Entities.OrderDto;
import com.smnk107.OrderService.Entities.Orders;
import com.smnk107.OrderService.Services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<String> createOrder(
            @RequestBody OrderDto orderDto
    ) {

        return ResponseEntity.ok(
                orderService.createOrder(orderDto.userId, orderDto.amount)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orders> getOrder(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                orderService.getOrder(id)
        );
    }
}