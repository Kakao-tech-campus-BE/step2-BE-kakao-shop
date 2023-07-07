package com.example.kakaoshop.cart.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CartOptionDTO {
    private int optionId;
    private int quantity;
}
