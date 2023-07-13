package com.example.kakaoshop.order.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRespFindByIdDTO {
  private int id;
  private List<ProductDTO> products;
  private int totalPrice;

  @Builder
  public OrderRespFindByIdDTO(int id, List<ProductDTO> products, int totalPrice) {
    this.id = id;
    this.products = products;
    this.totalPrice = totalPrice;
  }
}
