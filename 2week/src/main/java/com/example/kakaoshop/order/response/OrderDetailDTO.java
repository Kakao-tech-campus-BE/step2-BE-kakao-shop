package com.example.kakaoshop.order.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class OrderDetailDTO {
    private int id;
    private List<OrderDetailProductDTO> products;
    private int totalPrice;
}
