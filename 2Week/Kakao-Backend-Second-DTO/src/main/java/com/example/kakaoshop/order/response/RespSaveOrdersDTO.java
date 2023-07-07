package com.example.kakaoshop.order.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class RespSaveOrdersDTO {
    private int id;
    private List<OrderSaveProductDTO> products;
    private int totalPrice;

    @Builder
    public RespSaveOrdersDTO(int id, List<OrderSaveProductDTO> orders, int totalPrice) {
        this.id = id;
        this.products = orders;
        this.totalPrice = totalPrice;
    }
}
