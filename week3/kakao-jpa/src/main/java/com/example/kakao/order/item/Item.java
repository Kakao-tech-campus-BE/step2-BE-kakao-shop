package com.example.kakao.order.item;

import com.example.kakao.product.option.Option;
import com.example.kakao.order.Order;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="item_tb", indexes = {
        @Index(name = "item_option_id_idx", columnList = "option_id"),
        @Index(name = "item_order_id_idx", columnList = "order_id")
})
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // 1개 item은 1개 option이다
    @OneToOne(fetch = FetchType.LAZY)
    private Option option;

    // 여러 item을 1개 order가 가진다
    @ManyToOne
    private Order order;

    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private int price;


    @Builder
    public Item(int id, Option option, Order order, int quantity, int price) {
        this.id = id;
        this.option = option;
        this.order = order;
        this.quantity = quantity;
        this.price = price;
    }
}
