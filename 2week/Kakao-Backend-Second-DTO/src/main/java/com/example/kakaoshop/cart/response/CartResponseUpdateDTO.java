package com.example.kakaoshop.cart.response;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class CartResponseUpdateDTO {

    private List<CartProductDTO> orderProducts;
    private Integer totalPrice;

    @Builder
    public CartResponseUpdateDTO(List<CartProductDTO> orderProducts, Integer totalPrice) {
        this.orderProducts = orderProducts;
        this.totalPrice = totalPrice;
    }

}
