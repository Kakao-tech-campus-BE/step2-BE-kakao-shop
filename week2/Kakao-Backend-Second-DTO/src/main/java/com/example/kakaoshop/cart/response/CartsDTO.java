package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CartsDTO {
    private List<CartResUpdateDTO> carts;
    private int totalPrice;

    @Builder

    public CartsDTO(List<CartResUpdateDTO> carts, int totalPrice) {
        this.carts = carts;
        this.totalPrice = totalPrice;
    }
}
