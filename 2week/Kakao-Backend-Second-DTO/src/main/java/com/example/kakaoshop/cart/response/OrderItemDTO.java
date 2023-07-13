package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDTO {
    private int id;
    private int optionId;

    private String optionName;
    private int quantity;
    private int price;

    @Builder
    public OrderItemDTO(int id, ProductOptionDTO option, int quantity, int price) {
        this.id = id;
        this.optionId = option.getId();
        this.optionName = option.getOptionName();
        this.quantity = quantity;
        this.price = price;
    }
}
