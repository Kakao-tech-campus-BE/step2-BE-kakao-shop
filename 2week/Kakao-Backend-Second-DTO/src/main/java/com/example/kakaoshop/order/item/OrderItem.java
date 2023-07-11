package com.example.kakaoshop.order.item;

import com.example.kakaoshop.order.Order;
import com.example.kakaoshop.product.option.ProductOption;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="order_item_tb")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProductOption productOption;

    @Column(name="quantity", nullable=false)
    private int quantity;

    @Column(name="price", nullable=false)
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @Builder
    public OrderItem(int id, ProductOption productOption, int quantity, int price, Order order) {
        this.id = id;
        this.productOption = productOption;
        this.quantity = quantity;
        this.price = price;
        this.order = order;
    }
}