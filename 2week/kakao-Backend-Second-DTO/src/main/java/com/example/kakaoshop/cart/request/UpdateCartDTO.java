package com.example.kakaoshop.cart.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCartDTO {

  private int cardId;
  private int quantity;
}
