package com.example.kakaoshop.order.response.orderSave;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderSaveResDTO {

    private Long id;
    private OrderSaveProductDTO products;
    private Long totalPrice;
}
