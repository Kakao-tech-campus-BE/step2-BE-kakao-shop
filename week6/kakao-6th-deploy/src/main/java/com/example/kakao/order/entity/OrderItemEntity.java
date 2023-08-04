package com.example.kakao.order.entity;

import com.example.kakao.product.entity.ProductOptionEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "item_tb")
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private ProductOptionEntity option;
    @ManyToOne(fetch = FetchType.LAZY)
    private OrderEntity order;

    private int quantity;
    private int price;

    @Builder
    public OrderItemEntity(Long id, ProductOptionEntity option, OrderEntity order, int quantity, int price) {
        this.id = id;
        this.option = option;
        this.order = order;
        this.quantity = quantity;
        this.price = price;
    }
}