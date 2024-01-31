package com.uevitondev.deliveryapp.controller;

import com.uevitondev.deliveryapp.dto.OrderDTO;
import com.uevitondev.deliveryapp.dto.ShoppingCartDTO;
import com.uevitondev.deliveryapp.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> findAllOrders() {
        return ResponseEntity.ok().body(orderService.findAllOrders());
    }

    @GetMapping("/user")
    public ResponseEntity<List<OrderDTO>> findAllOrdersByUser() {
        return ResponseEntity.ok().body(orderService.findAllOrdersByUser());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> findOrderById(@PathVariable Long id) {
        return ResponseEntity.ok().body(orderService.findOrderById(id));
    }

    @PostMapping
    public ResponseEntity<OrderDTO> saveNewOrder(@RequestBody @Valid ShoppingCartDTO dto) {
        return ResponseEntity.ok().body(orderService.saveNewOrder(dto));
    }
}
