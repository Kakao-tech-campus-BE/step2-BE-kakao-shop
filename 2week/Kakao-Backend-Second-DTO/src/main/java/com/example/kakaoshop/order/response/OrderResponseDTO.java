package com.example.kakaoshop.order.response;

import com.example.kakaoshop.order.request.OrderProductDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderResponseDTO {
    private int id;
    private List<OrderProductDTO> products;
    private int totalPrice;
}