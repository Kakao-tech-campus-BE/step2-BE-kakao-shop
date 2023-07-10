package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartRespUpdateDTO {

    private List<UpdateCartItemDTO> carts;
    private long totalPrice;

    @Builder
    public CartRespUpdateDTO(List<UpdateCartItemDTO> carts, long totalPrice){
        this.carts = carts;
        this.totalPrice = totalPrice;
    }

}
