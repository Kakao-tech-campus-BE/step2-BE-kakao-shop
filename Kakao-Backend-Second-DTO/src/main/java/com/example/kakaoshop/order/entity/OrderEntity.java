package com.example.kakaoshop.order.entity;

import com.example.kakaoshop.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "order_entity")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private Long orderDate;
    private boolean isCancel;
    private int totalPrice;

    @Builder
    public OrderEntity(Long orderId, User user, Long orderDate, boolean isCancel, int totalPrice) {
        this.orderId = orderId;
        this.user = user;
        this.orderDate = orderDate;
        this.isCancel = isCancel;
        this.totalPrice = totalPrice;
    }
}