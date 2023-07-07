package com.example.kakaoshop.order;

import com.example.kakaoshop.order.response.OrderResponseDTO;
import com.example.kakaoshop.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderRestController {
    private final OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/save")
    public ResponseEntity<OrderResponseDTO> orderSave() {
        OrderResponseDTO response = orderService.createOrder();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> findById(@PathVariable int id) {
        OrderResponseDTO response = orderService.getOrder(id);
        return ResponseEntity.ok(response);
    }
}
