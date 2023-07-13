package com.example.kakaoshop.order.item;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderItemRespCheckDTO {
    private Long id;
    private List<OrderProjectDTO> orderProjectDTO;
    private int totalPrice;

    @Builder
    public OrderItemRespCheckDTO(Long id, List<OrderProjectDTO> orderProjectDTO, int totalPrice)
    {
        this.id = id;
        this.orderProjectDTO = orderProjectDTO;
        this.totalPrice = totalPrice;
    }
}
