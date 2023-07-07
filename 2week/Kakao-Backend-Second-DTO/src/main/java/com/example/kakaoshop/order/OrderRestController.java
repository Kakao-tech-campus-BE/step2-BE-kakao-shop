package com.example.kakaoshop.order;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.order.response.OrderResponseDTO;
import com.example.kakaoshop.order.response.OrderProductDTO;
import com.example.kakaoshop.order.response.OrderItemDTO;

import java.util.Arrays;

@RestController
@RequestMapping("/orders")
public class OrderRestController {

    @PostMapping("/save")
    public ResponseEntity<?> saveOrder() {
        OrderResponseDTO response = OrderResponseDTO.builder()
                .orderId(1)
                .products(Arrays.asList(
                        new OrderProductDTO("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", 
                                Arrays.asList(
                                        new OrderItemDTO(4, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10, 100000),
                                        new OrderItemDTO(5, "02. 슬라이딩 지퍼백 플라워에디션 5종", 10, 109000)
                                )
                        )
                ))
                .totalPrice(209000)
                .build();
        
        return ResponseEntity.ok(ApiUtils.success(response));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable int id) {
    	
        OrderItemDTO item1 = OrderItemDTO.builder()
        		.id(4)
        		.optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
        		.quantity(10)
        		.price(100000)
        		.build();

        OrderItemDTO item2 = OrderItemDTO.builder()
        		.id(5)
        		.optionName("02. 슬라이딩 지퍼백 플라워에디션 5종")
        		.quantity(10)
        		.price(109000)
        		.build();
        
        OrderProductDTO product1 = OrderProductDTO.builder()
        		.productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
        		.items(Arrays.asList(item1, item2))
        		.build();
    	
    	OrderResponseDTO response = OrderResponseDTO.builder()
    			.orderId(1)
    			.products(Arrays.asList(product1))
    			.totalPrice(209000)
    			.build();
    			

        return ResponseEntity.ok(ApiUtils.success(response));
    }
}

