package com.example.kakaoshop.order.response;

import com.example.kakaoshop.cart.response.CartRespProductDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderRespSaveDTO {
    private int id;
    private List<CartRespProductDTO> products;
    private int totalPrice;

    @Builder
    public OrderRespSaveDTO(int id, List<CartRespProductDTO> products, int totalPrice) {
        this.id = id;
        this.products = products;
        this.totalPrice = totalPrice;
    }
}
