package com.example.kakaoshop.cart;

import com.example.kakaoshop.product.Product;
import com.example.kakaoshop.product.option.ProductOption;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "cart_tb")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name ="user_id", nullable = false)
    private int userId;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProductOption productOption;

    @Column
    private int quantity;

    @Column
    private int price;

    @Builder
    public Cart(int id, int userId, ProductOption productOption, int quantity, int price){
        this.id = id;
        this.userId = userId;
        this.productOption = productOption;
        this.quantity = quantity;
        this.price = price;
    }

}
