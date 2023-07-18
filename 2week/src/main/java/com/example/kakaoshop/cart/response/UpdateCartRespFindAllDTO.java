package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class UpdateCartRespFindAllDTO {
    private List<UpdateCartItemDTO> products;
    private int totalPrice;

    @Builder
    public UpdateCartRespFindAllDTO(List<UpdateCartItemDTO> products, int totalPrice){
        this.products = products;
        this.totalPrice = totalPrice;
    }
}
