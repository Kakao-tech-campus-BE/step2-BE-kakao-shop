package com.example.kakaoshop.order.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDTO {
    private int cart_id;
    private String option_name;
    private int quantity;
    private int price;

    @Builder
    public OrderItemDTO(int cart_id, String option_name, int quantity, int price) {
        this.cart_id = cart_id;
        this.option_name = option_name;
        this.quantity = quantity;
        this.price = price;
    }
}
