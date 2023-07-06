package com.example.kakaoshop.order.response;

import com.example.kakaoshop.product.response.ProductOptionDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDTO {
    private String optionName;
    private int quantity;
    private int price;



    @Builder
    public OrderItemDTO(String optionName, int price, int quantity) {
        this.optionName = optionName;
        this.quantity = quantity;
        this.price = price;

    }
}
