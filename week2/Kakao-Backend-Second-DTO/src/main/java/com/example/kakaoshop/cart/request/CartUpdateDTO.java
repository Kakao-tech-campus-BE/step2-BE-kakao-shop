package com.example.kakaoshop.cart.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
public class CartUpdateDTO {
    private int cartId;
    private int quantity;
}
