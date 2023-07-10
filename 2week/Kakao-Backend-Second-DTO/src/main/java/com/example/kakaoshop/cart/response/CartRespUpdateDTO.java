package com.example.kakaoshop.cart.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartRespUpdateDTO {

  private List<UpdatedCartItemDTO> carts;
  private int totalPrice;

  @Builder
  public CartRespUpdateDTO(List<UpdatedCartItemDTO> carts, int totalPrice) {
    this.carts = carts;
    this.totalPrice = totalPrice;
  }
}
