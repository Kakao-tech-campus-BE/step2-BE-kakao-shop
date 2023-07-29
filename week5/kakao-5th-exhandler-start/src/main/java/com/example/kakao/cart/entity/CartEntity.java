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
@Table(name = "cart_entity",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "product_option_id"})
        })
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_option_id")
    private ProductOptionEntity productOptionEntity;

    private int quantity;

    @Builder
    public CartEntity(Long id, User user, ProductOptionEntity productOptionEntity, int quantity) {
        this.cartId = id;
        this.user = user;
        this.productOptionEntity = productOptionEntity;
        this.quantity = quantity;
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }
}

