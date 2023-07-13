package com.example.kakaoshop.cart;

import com.example.kakaoshop.product.option.ProductOption;
import com.example.kakaoshop.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="cart_tb")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    private ProductOption option;

    @Column(length = 11, nullable = false)
    private int price;

    @Builder
    public Cart(int id, User user, ProductOption option, int price){
        this.id = id;
        this.user = user;
        this.option = option;
        this.price = price;
    }

}
