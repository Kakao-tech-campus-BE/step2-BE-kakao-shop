package com.example.kakaoshop.cart;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Getter @Setter
@ToString
public class CartRequest {

//    @Min(1)
//    @NotNull
    private Long cartId;

//    @Min(0)
//    @NotNull
    private int quantity;
}
