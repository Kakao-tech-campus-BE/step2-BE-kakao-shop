package com.example.kakaoshop.cart;


import javax.persistence.*;

import com.example.kakaoshop.product.option.ProductOption;
import com.example.kakaoshop.user.User;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "cart_tb",
       uniqueConstraints = @UniqueConstraint(name = "uk_cart_option_user", columnNames = {"option_id", "user_id"}),
       indexes = {@Index(name = "cart_user_id_idx", columnList = "user_id"), 
                  @Index(name = "cart_option_id_idx", columnList = "option_id")})
public class Cart {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id", nullable = false)
    private ProductOption option;

    @Column(nullable = false)
    private int quantity;
    
    @Column(nullable = false)
    private int price;
    
    @Builder
    public Cart(int id, User user, ProductOption option, int quantity, int price) {
        this.id = id;
        this.user = user;
        this.option = option;
        this.quantity = quantity;
        this.price = price;
    }

}
