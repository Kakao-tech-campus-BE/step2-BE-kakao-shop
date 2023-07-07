package com.example.kakaoshop.cart.request;

import lombok.Data;

import java.util.List;

@Data
public class CartRequestUpdateDTO {

    private List<CartDTO> carts;
}
