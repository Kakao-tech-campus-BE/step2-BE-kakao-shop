package com.example.kakaoshop.order;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="order_tb")

public class Order {
    @Id
    private int id;
    private int totalPrice;

    @Builder
    public Order(int id, int totalPrice) {
        this.id = id;
        this.totalPrice = totalPrice;
    }
}
