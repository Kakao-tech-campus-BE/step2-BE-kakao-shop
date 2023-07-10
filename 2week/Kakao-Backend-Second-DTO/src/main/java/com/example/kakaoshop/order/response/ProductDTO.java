package com.example.kakaoshop.order.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {

  private String productName;
  private List<ProductItemDTO> items;

  @Builder
  public ProductDTO(String productName, List<ProductItemDTO> items) {
    this.productName = productName;
    this.items = items;
  }
}
