package com.example.kakaoshop.order.response.orderResult;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderResultItemDTO {

    private Long id;
    private String optionName;
    private Long quantity;
    private Long price;
}
