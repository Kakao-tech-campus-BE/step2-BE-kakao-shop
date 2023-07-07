package com.example.kakaoshop.order.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class OrderRespFindAllDTO {
    private int id;
    private ProductDTO products;
    private int totalPrice;

    @Builder
    public OrderRespFindAllDTO(int id, ProductDTO products, int totalPrice) {
        this.id = id;
        this.products = products;
        this.totalPrice = totalPrice;
    }
}