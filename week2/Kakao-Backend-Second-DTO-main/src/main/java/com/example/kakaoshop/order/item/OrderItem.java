package com.example.kakaoshop.order.item;

import com.example.kakaoshop.order.Order;
import com.example.kakaoshop.product.option.ProductOption;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "order_item_tb")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private ProductOption option;
    private int quantity;
    private int price;
    private Order order;
    @Builder
    public OrderItem(int id, ProductOption option, int quantity, int price, Order order){
        this.id = id;
        this.option = option;
        this.quantity = quantity;
        this.price = price;
        this.order = order;
    }
}
