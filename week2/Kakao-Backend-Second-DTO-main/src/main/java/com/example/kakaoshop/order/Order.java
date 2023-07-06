package com.example.kakaoshop.order;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "order_tb",
        indexes = {
                @Index(name = "order_user_id_idx", columnList = "user_id")
        })
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Builder
    public Order(int id, int userId) {
        this.id = id;
        this.userId = userId;
    }
}
