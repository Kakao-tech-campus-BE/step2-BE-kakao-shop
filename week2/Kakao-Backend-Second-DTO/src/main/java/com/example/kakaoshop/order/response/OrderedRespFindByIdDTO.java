package com.example.kakaoshop.order.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderedRespFindByIdDTO {
    private int id;
    private List<OrderedProductDTO> orderedProducts;
    private long totalPrice;

    @Builder
    public OrderedRespFindByIdDTO(int id, List<OrderedProductDTO> orderedProducts, long totalPrice){
        this.id = id;
        this.orderedProducts = orderedProducts;
        this.totalPrice = totalPrice;
    }
}
