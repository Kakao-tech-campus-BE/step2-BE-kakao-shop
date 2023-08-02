package com.example.kakao.order;

import com.example.kakao.order.item.Item;
import com.example.kakao.product.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class OrderResponse {


  @Getter@Setter
  public static class FindByIdDTO {
    private int id;// 주문 번호
    private List<ProductDTO> products;
    private int totalPrice;

    public FindByIdDTO(int id, List<Item> itemList){
      this.id = id;
      this.products = itemList.stream()
          .map(item -> new ProductDTO(item.getOption().getProduct().getProductName(), item))
          .collect(Collectors.toList());
      this.totalPrice = itemList.stream()
          .mapToInt(item -> item.getPrice() * item.getQuantity())
          .sum();
    }

    @Getter
    @Setter
    public static class ProductDTO {
      private String productName;
      private List<ItemDTO> items;

      public ProductDTO(String productName, Item item){
        this.productName = productName;
        this.items = List.of(new ItemDTO(item));
      }
    }
    @Getter@Setter
    public static class ItemDTO {
      private int id;
      private int quantity;
      private int price;
      private String optionName;

      public ItemDTO(Item item){
        this.id = item.getId();
        this.quantity = item.getQuantity();
        this.price = item.getPrice();
        this.optionName = item.getOption().getOptionName();
      }
    }
  }

  @Getter
  @Setter
  public static class SaveDTO {
    private int id;
    private List<ProductDTO> products;
    private int totalPrice;

    public SaveDTO(Order order, List<Item> itemList) {
      this.id = order.getId();
      this.products = itemList.stream()
          .map(item -> new ProductDTO(item.getOption().getProduct(), item))
          .collect(Collectors.toList());
      this.totalPrice = itemList.stream()
          .mapToInt(item -> item.getPrice() * item.getQuantity())
          .sum();
    }

    @Getter
    @Setter
    public static class ProductDTO {
      private String productName;
      private List<ItemDTO> items;

      public ProductDTO(Product product, Item item) {
        this.productName = product.getProductName();
        this.items = List.of(new ItemDTO(item));
      }

      @Getter
      @Setter
      public static class ItemDTO {
        private int id;
        private String optionName;
        private int quantity;
        private int price;

        public ItemDTO(Item item) {
          this.id = item.getId();
          this.optionName = item.getOption().getOptionName();
          this.quantity = item.getQuantity();
          this.price = item.getPrice();
        }
      }
    }
  }
}