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
@Entity // DB 테이블에 대응하는 하나의 클래스임을 의미
@Table(name="order_item_tb",
            indexes = {
                    @Index(name = "order_item_option_id_idx", columnList = "option_id"),
                    @Index(name = "order_item_order_id_idx", columnList = "order_id")
            })
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성을 DB에 위임
    private int id;

    @ManyToOne(fetch = FetchType.LAZY) // 왜 지연로딩을 쓰는지 알아보자.
    @JoinColumn(name = "option_id", foreignKey = @ForeignKey(name = "order_item_option_id"))
    private ProductOption productOption;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", foreignKey = @ForeignKey(name = "order_item_order_id"))
    private Order order;

    @Builder
    public OrderItem(int id, ProductOption productOption, int quantity, int price, Order order){
        this.id = id;
        this.productOption = productOption;
        this.quantity = quantity;
        this.price = price;
        this.order = order;
    }
}
