package com.example.kakaoshop.order.response.orderResult;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderResultProductDTO {

    private String productName;
    private List<OrderResultItemDTO> items;
}
