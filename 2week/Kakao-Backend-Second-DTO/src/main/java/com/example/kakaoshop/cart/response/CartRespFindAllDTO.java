package com.example.kakaoshop.cart.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartRespFindAllDTO {

  private List<ProductDTO> products;
  private int totalPrice;

  @Builder
  public CartRespFindAllDTO(List<ProductDTO> products, int totalPrice) {
    this.products = products;
    this.totalPrice = totalPrice;
  }
}
