package com.example.kakaoshop.order.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class RespFindByIdDTO {
    private int id;
    private List<OrderResultProductDTO> products;
    private int totalPrice;

    @Builder
    public RespFindByIdDTO(int id, List<OrderResultProductDTO> orders, int totalPrice) {
        this.id = id;
        this.products = orders;
        this.totalPrice = totalPrice;
    }
}
