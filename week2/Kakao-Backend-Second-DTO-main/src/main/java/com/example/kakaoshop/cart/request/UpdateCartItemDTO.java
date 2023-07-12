package com.example.kakaoshop.cart.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateCartItemDTO {
    private int cartId;
    private int quantity;
}
