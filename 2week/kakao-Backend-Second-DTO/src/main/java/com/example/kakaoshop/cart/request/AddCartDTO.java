package com.example.kakaoshop.cart.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AddCartDTO {
    private int optionId;
    private int quantity;
}
