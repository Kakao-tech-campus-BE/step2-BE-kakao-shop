package com.example.kakaoshop.order.item;

import com.example.kakaoshop.order.Order;
import com.example.kakaoshop.product.option.ProductOption;
import com.example.kakaoshop.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.ast.Or;

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
    private Order order;

    @OneToOne(fetch = FetchType.LAZY)
    private ProductOption option;

    @Column(length = 10000, nullable = false)
    private int quantity;

    @Column(length = 11, nullable = false)
    private int price;

    @Builder
    public OrderItem(int id, Order order, ProductOption option, int quantity, int price){
        this.id = id;
        this.order = order;
        this.option = option;
        this.quantity = quantity;
        this.price = price;
    }
}
