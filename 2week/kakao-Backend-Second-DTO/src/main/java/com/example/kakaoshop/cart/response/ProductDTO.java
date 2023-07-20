package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductDTO {

  private int id;
  private String productName;
  private String description;
  private String image;
  private int price;

  @Builder
  public ProductDTO(int id, String productName, String description, String image, int price) {
    this.id = id;
    this.productName = productName;
    this.description = description;
    this.image = image;
    this.price = price;
  }
}
