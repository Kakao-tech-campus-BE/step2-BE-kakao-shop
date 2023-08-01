package com.example.kakao.domain.cart;

import com.example.kakao.domain.product.option.Option;
import com.example.kakao.domain.user.User;
import lombok.*;

import javax.persistence.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="cart_tb",
        indexes = {
                @Index(name = "cart_user_id_idx", columnList = "user_id"),
                @Index(name = "cart_option_id_idx", columnList = "option_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_cart_option_user", columnNames = {"user_id", "option_id"})
        })
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // 삭제가 빈번히 발생할것.. uuid 가 적절하지 않을까 ?

    @ManyToOne(fetch = FetchType.LAZY)
    private User user; // user별로 장바구니에 묶여 있음.

    @OneToOne(fetch = FetchType.LAZY)
    private Option option;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private long price; // total price

    @Builder
    public Cart(int id, User user, Option option, int quantity, long price) {
        this.id = id;
        this.user = user;
        this.option = option;
        this.quantity = quantity;
        this.price = price;
    }

    // 장바구니 업데이트
    public void update(int quantity, long price){
        this.quantity = quantity;
        this.price = price;
    }
}