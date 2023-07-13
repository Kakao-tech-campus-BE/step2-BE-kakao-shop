package com.example.kakaoshop.order.response.orderSave;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderSaveItemDTO {

    private Long id;
    private String optionName;
    private Long quantity;
    private Long price;
}
