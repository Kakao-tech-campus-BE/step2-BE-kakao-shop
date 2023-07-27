package com.example.kakao.order.item;

import com.example.kakao.product.option.ProductOption;
import com.example.kakao.order.Order;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
// 테이블 명 수정
@Table(name="order_item_tb", indexes = {
        @Index(name = "item_option_id_idx", columnList = "product_option_id"),
        @Index(name = "item_order_id_idx", columnList = "order_id")
})

// class명 수정
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    private ProductOption productOption;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int price;


    @Builder
    public OrderItem(int id, ProductOption productOption, Order order, int quantity, int price) {
        this.id = id;
        this.productOption = productOption;
        this.order = order;
        this.quantity = quantity;
        this.price = price;
    }
}
