package com.example.kakao.order.item;

import com.example.kakao.product.option.ProductOption;
import com.example.kakao.order.Order;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity

// 테이블명 변경
@Table(name="order_item_tb", indexes = {
        @Index(name = "item_option_id_idx", columnList = "product_option_id"),
        @Index(name = "item_order_id_idx", columnList = "order_id")
})

// 클래스명 변경
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    // 유효성 검사 추가
    @NotNull
    private ProductOption productOption;

    @ManyToOne(fetch = FetchType.LAZY)
    // 유효성 검사 추가
    @NotNull
    private Order order;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int totalPrice;

    // 유효성 검사 추가 NotNull
    @Builder
    public OrderItem(int id, @NotNull ProductOption productOption, @NotNull Order order, int quantity, int totalPrice) {
        this.id = id;
        this.productOption = productOption;
        this.order = order;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
}
