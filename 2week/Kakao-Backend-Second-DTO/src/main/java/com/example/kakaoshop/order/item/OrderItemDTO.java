package com.example.kakaoshop.order.item;

import com.example.kakaoshop.cart.response.ProductDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class OrderItemDTO {
    private Long id;
    private String optionName;
    private int quantity;
    private int price;

    @Builder
    public OrderItemDTO(Long id, String optionName, int quantity, int price)
    {
        this.id = id;
        this.optionName = optionName;
        this.quantity = quantity;
        this.price = price;
    }
}
