package com.example.kakaoshop.order.item;

import com.example.kakaoshop.cart.response.ProductOptionDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDTO {
    private int id;
    private ProductOptionDTO option;
    private int quantity;
    private int price;

    @Builder
    public OrderItemDTO(int id, ProductOptionDTO option, int quantity, int price) {
        this.id = id;
        this.option = option;
        this.quantity = quantity;
        this.price = price;
    }
}
