package com.example.kakaoshop.order.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderSaveResDTO {
    private int id;
    private List<ProductOptionItemDTO> products;
    private int totalPrice;

    @Builder
    public OrderSaveResDTO(int id, List<ProductOptionItemDTO> products, int totalPrice){
        this.id = id;
        this.products = products;
        this.totalPrice = totalPrice;
    }
}
