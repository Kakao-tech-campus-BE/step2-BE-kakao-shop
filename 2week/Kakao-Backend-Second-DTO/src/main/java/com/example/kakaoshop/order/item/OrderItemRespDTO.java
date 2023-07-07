package com.example.kakaoshop.order.item;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class OrderItemRespDTO {
    private Long id;
    private List<OrderProjectDTO> orderProjectDTO;
    private int totalPrice;

    public OrderItemRespDTO(Long id, List<OrderProjectDTO> orderProjectDTO, int totalPrice)
    {
        this.id = id;
        this.orderProjectDTO = orderProjectDTO;
        this.totalPrice = totalPrice;
    }
}
