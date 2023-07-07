package com.example.kakaoshop.cart.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartUpdateReqDTO {
    private Long cartId;
    private Long quantity;
}
