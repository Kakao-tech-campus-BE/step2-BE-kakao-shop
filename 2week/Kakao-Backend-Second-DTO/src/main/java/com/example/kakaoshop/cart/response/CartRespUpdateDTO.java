package com.example.kakaoshop.cart.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder

public class CartRespUpdateDTO{

    private List<CartDTO> carts;
    private int totalPrice;



}
