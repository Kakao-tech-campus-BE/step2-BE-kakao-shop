package com.example.kakao.order.item;

import com.example.kakao.cart.Cart;
import com.example.kakao.product.option.Option;
import com.example.kakao.order.Order;
import lombok.*;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToOne(fetch = FetchType.LAZY)
    private Option option;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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

    private static Item createItem(Cart cart, Order order){
        return Item.builder()
                .order(order)
                .option(cart.getOption())
                .quantity(cart.getQuantity())
                .price(cart.getOption().getPrice() * cart.getQuantity())
                .build();
    }
    public static List<Item> createItems(List<Cart> carts, Order order){
        List<Item> items = new ArrayList<>();
        for(Cart cart : carts){
            Item item = createItem(cart, order);
            items.add(item);
        }
        return items;
    }


}
