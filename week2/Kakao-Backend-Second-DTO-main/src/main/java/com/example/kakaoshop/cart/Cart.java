package com.example.kakaoshop.cart;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "cart_tb")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int userId;

    @Column(nullable = false)
    private int optionId;

    private int quantity;

    private int price;

    @Builder
    public Cart(int id, int userId, int optionId, int quantity, int price){
        this.id = id;
        this.userId = userId;
        this.optionId = optionId;
        this.quantity = quantity;
        this.price = price;
    }
}
