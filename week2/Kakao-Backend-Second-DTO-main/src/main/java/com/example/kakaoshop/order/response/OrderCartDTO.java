package com.example.kakaoshop.order.response;

import com.example.kakaoshop.cart.response.CartRespFindAllDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderCartDTO {

    private int id;
    private CartRespFindAllDTO products;
    private int totalprice;

    @Builder
    public OrderCartDTO(int id, int price, CartRespFindAllDTO cartRespFindAllDTO) {
        this.id = id;
        this.totalprice = price;
        this.products = cartRespFindAllDTO;
    }
}
