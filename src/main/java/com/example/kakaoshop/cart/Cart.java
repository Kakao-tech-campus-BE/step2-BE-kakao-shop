package com.example.kakaoshop.cart;

import com.example.kakaoshop.product.option.ProductOption;
import com.example.kakaoshop.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Cart {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @OneToOne
  @JoinColumn(name = "product_option_id")
  private ProductOption productOption;

  private int quantity;

  private int price;

  @Builder
  public Cart(User user, ProductOption productOption, int quantity, int price) {
    this.user = user;
    this.productOption = productOption;
    this.quantity = quantity;
    this.price = price;
  }

}
