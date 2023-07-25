package com.example.kakao.domain.cart.dto.response;

import com.example.kakao.domain.cart.Cart;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class UpdateResponseDTO {
  private List<CartDTO> carts;
  private int totalPrice;

  public UpdateResponseDTO(List<Cart> cartList) {
    this.carts = cartList.stream().map(CartDTO::new).collect(Collectors.toList());
    this.totalPrice = cartList.stream().mapToInt(Cart::getPrice).sum();
  }


  @Getter
  @Setter
  public static class CartDTO {
    private int cartId;
    private int optionId;
    private String optionName;
    private int quantity;
    private int price;

    private CartDTO(Cart cart) {
      this.cartId = cart.getId();
      this.optionId = cart.getOption().getId();
      this.optionName = cart.getOption().getOptionName();
      this.quantity = cart.getQuantity();
      this.price = cart.getPrice();
    }
  }
}