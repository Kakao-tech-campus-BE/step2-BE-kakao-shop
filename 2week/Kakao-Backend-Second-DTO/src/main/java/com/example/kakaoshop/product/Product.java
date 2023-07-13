package com.example.kakaoshop.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "product_tb")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(length = 100, nullable = false)
  private String productName;

  @Column(length = 1000, nullable = false)
  private String description;

  @Column(length = 500)
  private String image;

  private int price;

  @Builder
  public Product(int id, String productName, String description, String image, int price) {
    this.id = id;
    this.productName = productName;
    this.description = description;
    this.image = image;
    this.price = price;
  }
}
