// OrderRestController
package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.order.request.OrderItemDTO;
import com.example.kakaoshop.order.request.OrderProductDTO;
import com.example.kakaoshop.order.request.OrderRequestDTO;
import com.example.kakaoshop.order.response.OrderResponseDTO;
import com.example.kakaoshop.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;

@RestController
@RequestMapping("/api")
public class MockOrderController {

    @PostMapping("/orders/save")
    public ResponseEntity<OrderRequestDTO> orderSave(@RequestHeader("Authorization") String token) {
        // Mock OrderItems
        OrderItemDTO orderItem1 = OrderItemDTO.builder()
                .id(4)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(100000)
                .build();

        OrderItemDTO orderItem2 = OrderItemDTO.builder()
                .id(5)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build();

        // Mock Products
        OrderProductDTO product = OrderProductDTO.builder()
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                .items(Arrays.asList(orderItem1, orderItem2))
                .build();

        // Mock Order
        OrderRequestDTO order = OrderRequestDTO.builder()
                .id(2)
                .products(Collections.singletonList(product))
                .totalPrice(209000)
                .build();

        return ResponseEntity.ok(order);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderRequestDTO> findById(@PathVariable int id, @RequestHeader("Authorization") String token) {
        // Returning the same mock data as /orders/save for simplicity
        // In a real application, this should retrieve the order based on the id from the database

        // Mock OrderItems
        OrderItemDTO orderItem1 = OrderItemDTO.builder()
                .id(4)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .quantity(10)
                .price(100000)
                .build();

        OrderItemDTO orderItem2 = OrderItemDTO.builder()
                .id(5)
                .optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
                .quantity(10)
                .price(109000)
                .build();

        // Mock Products
        OrderProductDTO product = OrderProductDTO.builder()
                .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                .items(Arrays.asList(orderItem1, orderItem2))
                .build();

        // Mock Order
        OrderRequestDTO order = OrderRequestDTO.builder()
                .id(id)
                .products(Collections.singletonList(product))
                .totalPrice(209000)
                .build();

        return ResponseEntity.ok(order);
    }
}
