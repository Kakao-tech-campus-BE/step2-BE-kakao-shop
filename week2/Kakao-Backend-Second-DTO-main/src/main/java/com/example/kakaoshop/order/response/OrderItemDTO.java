package com.example.kakaoshop.order.response;

import com.example.kakaoshop.cart.response.CartProductDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class OrderItemDTO {

    private int id;
    private List<CartProductDTO> products;
    private int totalprice;

    @Builder
    public OrderItemDTO(int id, int price, List<CartProductDTO> cartRespFindAllDTO) {
        this.id = id;
        this.totalprice = price;
        this.products = cartRespFindAllDTO;
    }
}
