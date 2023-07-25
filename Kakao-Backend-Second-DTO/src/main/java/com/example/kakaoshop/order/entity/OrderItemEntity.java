package com.example.kakaoshop.order.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "order_item_entity")
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity orderEntity;
    private Long optionId;
    private Long productId;
    @Column(length = 30, nullable = false)
    private String optionName;
    @Column(length = 20, nullable = false)
    private String productName;
    private int price;
    private int quantity;

    @Builder
    public OrderItemEntity(Long orderItemId, OrderEntity orderEntity, Long optionId, Long productId, String optionName, String productName, int price, int quantity) {
        this.orderItemId = orderItemId;
        this.orderEntity = orderEntity;
        this.optionId = optionId;
        this.productId = productId;
        this.optionName = optionName;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }
}