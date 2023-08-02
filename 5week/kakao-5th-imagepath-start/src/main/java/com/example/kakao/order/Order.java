package com.example.kakao.order;

import com.example.kakao.cart.Cart;
import com.example.kakao.order.item.Item;
import com.example.kakao.product.Product;
import com.example.kakao.product.option.Option;
import com.example.kakao.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="order_tb", indexes = {
        @Index(name = "order_user_id_idx", columnList = "user_id")
})
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    /*
    @OneToMany(fetch = FetchType.LAZY)
    private Item item;
    */
    /*

    @ManyToOne(fetch = FetchType.LAZY) // ?????
    private Option option; // 리스트로 해야하나...? 아 Cart로 해야할 것 같기도 하고...

    private int quantity;

    private long price;
    item으로 다 끌어오는 식으로 해보자.

    */


    @Builder
    public Order(int id, User user) {
        this.id = id;
        this.user = user;
    }
}
