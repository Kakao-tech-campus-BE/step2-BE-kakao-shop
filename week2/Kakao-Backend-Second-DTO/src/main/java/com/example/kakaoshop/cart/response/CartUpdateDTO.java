package com.example.kakaoshop.cart.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartUpdateDTO {
    private List<UpdateItemDTO> carts;
    private int totalPrice;
    public CartUpdateDTO(List<UpdateItemDTO> carts, int totalPrice){
        this.carts = carts;
        this.totalPrice = totalPrice;
    }
}

