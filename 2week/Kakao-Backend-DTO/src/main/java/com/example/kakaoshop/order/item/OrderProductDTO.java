package com.example.kakaoshop.order.item;

import com.example.kakaoshop.cart.response.CartItemDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderProductDTO {
    private String productName;
    private List<OrderItem> items;

    @Builder
    public OrderProductDTO(String productName, List<OrderItem> items) {
        this.productName = productName;
        this.items = items;
    }
}

