package com.example.kakaoshop.cart.entity;

import com.example.kakaoshop.product.entity.ProductOptionEntity;
import com.example.kakaoshop.user.User;
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
                @UniqueConstraint(columnNames = {"user_id", "product_option_id"})
        })
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProductOptionEntity productOption;

    private int quantity;

    @Builder
    public CartEntity(Long id, User user, ProductOptionEntity productOptionEntity, int quantity) {
        this.id = id;
        this.user = user;
        this.productOption = productOptionEntity;
        this.quantity = quantity;
    }
}

