package com.example.kakaoshop.order.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class OrderRespAddDTO {
    private int id;
    private List<ProductRespOrderAddDTO> products;
    private int totalPrice;

    @Builder
    public OrderRespAddDTO(int id, List<ProductRespOrderAddDTO> products, int totalPrice) {
        this.id = id;
        this.products = products;
        this.totalPrice = totalPrice;
    }
}
