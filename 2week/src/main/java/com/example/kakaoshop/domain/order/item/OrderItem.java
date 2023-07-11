package com.example.kakaoshop.domain.order.item;


import com.example.kakaoshop.domain.order.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@NoArgsConstructor
public class OrderItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @NotBlank
  private String productName;

  @NotBlank
  private String optionName;

  private int price;

  private int quantity;

  @ManyToOne
  @JoinColumn(name = "order_id")
  private Order order;

  @Builder
  public OrderItem(String productName, String optionName, int price, int quantity, Order order) {
    this.productName = productName;
    this.optionName = optionName;
    this.price = price;
    this.quantity = quantity;
    this.order = order;
  }

}
