package com.example.kakaoshop.order.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class OrderRespSaveOrderDTO {
    private List<ProductDTO> products;
    private int totalPrice;

    @Builder
    public OrderRespSaveOrderDTO(List<ProductDTO> products, int totalPrice) {
        this.products = products;
        this.totalPrice = totalPrice;
    }
}