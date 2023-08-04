package com.example.kakao.cart;

import com.example.kakao.product.option.Option;
import com.example.kakao.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user; // user별로 장바구니에 묶여 있음.

    @OneToOne(fetch = FetchType.LAZY)
    private Option option;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int price;

    @Builder
    public Cart(int id, User user, Option option, int quantity, int price) {
        this.id = id;
        this.user = user;
        this.option = option;
        this.quantity = quantity;
        this.price = price;
    }

    // 장바구니 업데이트
    public void update(int quantity, int price){
        this.quantity = quantity;
        this.price = price;
    }
}