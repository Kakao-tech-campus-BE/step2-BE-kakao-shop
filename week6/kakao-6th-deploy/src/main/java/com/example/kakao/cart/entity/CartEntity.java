package com.example.kakao.cart.entity;

import com.example.kakao.product.entity.ProductOptionEntity;
import com.example.kakao.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "cart_tb",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "option_id"})
        })
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProductOptionEntity option;

    private int quantity;
    private int price;

    @Builder
    public CartEntity(Long id, User user, ProductOptionEntity option, int quantity, int price) {
        this.id = id;
        this.user = user;
        this.option = option;
        this.quantity = quantity;
        this.price = price;
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }
}

