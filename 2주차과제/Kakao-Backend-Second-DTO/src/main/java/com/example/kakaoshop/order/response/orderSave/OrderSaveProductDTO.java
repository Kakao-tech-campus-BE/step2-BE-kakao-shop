package com.example.kakaoshop.order.response.orderSave;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderSaveProductDTO {

    private String productName;
    private List<OrderSaveItemDTO> items;
}
