package com.example.kakao.domain.cart.dto.response;

import com.example.kakao.domain.cart.Cart;
import com.example.kakao.domain.product.Product;
import com.example.kakao.domain.product.option.Option;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class FindAllResponseDTO {
  private List<ProductDTO> products;
  private long totalPrice;

  public FindAllResponseDTO(List<Cart> cartList) {
    this.products = cartList.stream()
      // 중복되는 상품 걸러내기
      .map(cart -> cart.getOption().getProduct()).distinct()
      .map(product -> new ProductDTO(product, cartList)).collect(Collectors.toList());
    this.totalPrice = cartList.stream().mapToLong(cart -> cart.getOption().getPrice() * cart.getQuantity()).sum();
  }


  @Getter
  @Setter
  public static class ProductDTO {
    private int id;
    private String productName;
    private List<CartDTO> carts;

    public ProductDTO(Product product, List<Cart> cartList) {
      this.id = product.getId();
      this.productName = product.getProductName();
      // 현재 상품과 동일한 장바구니 내역만 담기
      this.carts = cartList.stream()
        .filter(cart -> cart.getOption().getProduct().getId() == product.getId())
        .map(CartDTO::new)
        .collect(Collectors.toList());
    }

    @Getter
    @Setter
    public static class CartDTO {
      private int id;
      private OptionDTO option;
      private int quantity;
      private long price;

      public CartDTO(Cart cart) {
        this.id = cart.getId();
        this.option = new OptionDTO(cart.getOption());
        this.quantity = cart.getQuantity();
        this.price = cart.getOption().getPrice() * cart.getQuantity();
      }

      @Getter
      @Setter
      public static class OptionDTO {
        private int id;
        private String optionName;
        private long price;

        public OptionDTO(Option option) {
          this.id = option.getId();
          this.optionName = option.getOptionName();
          this.price = option.getPrice();
        }
      }
    }
  }
}