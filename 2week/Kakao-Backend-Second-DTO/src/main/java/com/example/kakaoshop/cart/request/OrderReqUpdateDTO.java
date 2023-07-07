package com.example.kakaoshop.cart.request;

import lombok.Getter;

@Getter
public class OrderReqUpdateDTO {
    private int cartId;
    private int quantity;
}
