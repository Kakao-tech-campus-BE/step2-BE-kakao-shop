package com.example.kakaoshop.order.response.orderResult;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderResultResDTO {

    private Long id;
    private OrderResultProductDTO products;
    private Long totalPrice;
}
