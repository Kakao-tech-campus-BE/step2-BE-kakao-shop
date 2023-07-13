package com.example.kakaoshop.cart.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
public class OrderDetail {
    private int price;
    private int quantity;

    public static OrderDetail newInstance(Integer price, Integer quantity) {
        return new OrderDetail(price, quantity);
    }

    public int calculate() {
        return price * quantity;
    }
}
