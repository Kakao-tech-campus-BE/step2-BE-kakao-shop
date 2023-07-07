package com.example.kakaoshop.cart.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartAddReqDTO {
    private Long optionId;
    private Long quantity;
}
