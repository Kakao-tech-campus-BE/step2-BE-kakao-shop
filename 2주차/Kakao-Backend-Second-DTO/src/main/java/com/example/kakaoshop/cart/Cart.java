package com.example.kakaoshop.cart;

import com.example.kakaoshop.product.Product;
import com.example.kakaoshop.product.option.ProductOption;
import com.example.kakaoshop.user.User;
import lombok.*;

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

    @OneToOne
    private ProductOption productOption;

    @Column
    private int quantity;

    private int price;

    @Builder
    public Cart(int id,User user,ProductOption productOption,int quantity,int price){
        this.id = id;
        this.user = user;
        this.productOption = productOption;
        this.quantity = quantity;
        this.price = price;
    }

}
