package com.example.kakao.cart;

import com.example.kakao.product.option.ProductOption;
import com.example.kakao.user.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


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

    //@ManyToOne(fetch = FetchType.LAZY)
    // Eager 로딩을 설정하여 관련된 엔티티를 함께 로딩
    // 이를 통해 N+1 문제를 방지
    @ManyToOne(fetch = FetchType.EAGER) // 수정
    @JoinColumn(name = "user_id") // 추가
    @NotNull // 유효성 검사 추가
    private User user; // user별로 장바구니에 묶여 있음.

    //@OneToOne(fetch = FetchType.LAZY)
    @OneToOne(fetch = FetchType.EAGER) // 수정
    @JoinColumn(name = "option_id") // 추가
    @NotNull // 유효성 검사 추가
    private ProductOption productOption;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int price;

    // 유효성 검사 추가
    @Builder
    public Cart(int id, @NotNull User user, @NotNull ProductOption productOption, int quantity, int price) {
        this.id = id;
        this.user = user;
        this.productOption = productOption;
        this.quantity = quantity;
        this.price = price;
    }

    // 장바구니 업데이트
    public void update(int quantity, int price){
        this.quantity = quantity;
        this.price = price;
    }
}