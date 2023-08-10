package com.example.kakao.cart.domain.model;

import lombok.AllArgsConstructor;
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
