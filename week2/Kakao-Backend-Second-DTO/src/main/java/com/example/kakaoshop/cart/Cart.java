package com.example.kakaoshop.cart;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="cart_tb")

public class Cart {
    @Id
    private int id;
    private String productOption;
    private int price;
    private int quantity;


    @Builder
    public Cart(int id, String productOption, int quantity, int price) {
        this.id = id;
        this.productOption = productOption;
        this.price = price;
        this.quantity = quantity;
    }
}