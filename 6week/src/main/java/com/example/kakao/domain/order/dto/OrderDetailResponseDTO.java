package com.example.kakao.domain.order.dto;


import com.example.kakao.domain.order.Order;
import com.example.kakao.domain.order.item.Item;
import com.example.kakao.domain.product.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class OrderDetailResponseDTO {

  private int id; // = orderId. API 문서 상의 json preperty 와 이름을 맞추기 위함.

  private List<ProductDTO> products;

  private long totalPrice;

  public OrderDetailResponseDTO(Order order, List<Item> items) {
    this.id = order.getId();
    this.products = items.stream()
      .map(item -> item.getOption().getProduct()).distinct()
      .map(product -> new ProductDTO(product, items)).collect(Collectors.toList());
    this.totalPrice = items.stream().mapToLong(Item::getPrice).sum();
  }

  @Getter
  @Setter
  public static class ProductDTO {
    private String productName;
    private List<ItemDTO> items;

    private ProductDTO(Product product, List<Item> items) {
      this.productName = product.getProductName();
      this.items = items.stream()
        .filter(item -> item.getOption().getProduct().getId() == product.getId())
        .map(ItemDTO::new)
        .collect(Collectors.toList());
    }
  }

  @Getter
  @Setter
  public static class ItemDTO {
    private int id;
    private String optionName;
    private int quantity;
    private long price;

    private ItemDTO(Item item) {
      this.id = item.getId();
      this.optionName = item.getOption().getOptionName();
      this.quantity = item.getQuantity();
      this.price = item.getPrice();
    }
  }

}
