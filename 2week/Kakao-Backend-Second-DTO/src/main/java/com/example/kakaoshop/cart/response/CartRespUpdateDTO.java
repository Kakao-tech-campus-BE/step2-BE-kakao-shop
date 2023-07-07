package com.example.kakaoshop.cart.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartRespUpdateDTO {
    private List<CartUpdateDTO> cartUpdateDTO;
    private int totalPrice;

    public CartRespUpdateDTO(List<CartUpdateDTO> cartUpdateDTO, int totalPrice)
    {
        this.cartUpdateDTO = cartUpdateDTO;
        this.totalPrice = totalPrice;
    }

}
