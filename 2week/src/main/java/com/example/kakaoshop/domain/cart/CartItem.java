package com.example.kakaoshop.domain.cart;

import com.example.kakaoshop.domain.account.Account;
import com.example.kakaoshop.domain.product.option.ProductOption;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class CartItem {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  @JoinColumn(name = "account_id")
  private Account account;

  @ManyToOne
  @JoinColumn(name = "product_option_id")
  private ProductOption productOption;

  private int quantity;

  private int price;

  @Builder
  public CartItem(Account account, ProductOption productOption, int quantity, int price) {
    this.account = account;
    this.productOption = productOption;
    this.quantity = quantity;
    this.price = price;
  }

}
