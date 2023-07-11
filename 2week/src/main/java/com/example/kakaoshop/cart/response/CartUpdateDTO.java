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
public class CartUpdateDTO {
    private List<UpdateItemDTO> carts;
    private int totalPrice;
}
