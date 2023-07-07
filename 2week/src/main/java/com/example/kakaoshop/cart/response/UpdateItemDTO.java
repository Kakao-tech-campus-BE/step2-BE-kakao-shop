package com.example.kakaoshop.cart.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UpdateItemDTO {
    private int cartId;
    private int optionId;
    private String optionName;
    private int quantity;
    private int price;
}
