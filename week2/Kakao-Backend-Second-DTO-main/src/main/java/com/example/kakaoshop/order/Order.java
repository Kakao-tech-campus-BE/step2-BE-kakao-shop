package com.example.kakaoshop.order;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="order_tb")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int user_id;


    @Builder
    public Order(int id, int user_id) {
        this.id = id;
        this.user_id = user_id;
    }

}
