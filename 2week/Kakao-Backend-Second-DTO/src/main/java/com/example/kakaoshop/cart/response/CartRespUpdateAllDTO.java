package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CartRespUpdateAllDTO {
    private List<OrderItemDTO> orders;
    private int totalPrice;

    @Builder
    public CartRespUpdateAllDTO(List<OrderItemDTO> orders, int totalPrice) {
        this.orders = orders;
        this.totalPrice = totalPrice;
    }
}
