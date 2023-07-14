package com.example.kakao.order_item;

import com.example.kakao.option.Option;
import com.example.kakao.order.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "order_item_tb")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "option_id", nullable = false)
    private Option option;
    private int price;
    private int quantity;
}
