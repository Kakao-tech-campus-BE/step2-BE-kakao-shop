package com.example.kakaoshop.order.item;


import com.example.kakaoshop.product.option.ProductOption;
import lombok.*;

import javax.persistence.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="order_item_tb")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProductOption option;

    private int quantity;
    private int price;

    @Builder
    public OrderItem(int id, ProductOption option, int quantity, int price){
        this.id = id;
        this.option = option;
        this.quantity = quantity;
        this.price = price;
    }

}
