package com.example.kakaoshop.domain.cart.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class CartRequest {

  private CartRequest() {}

  @Getter
  @RequiredArgsConstructor
  @Builder
  public static class CartItemAddDto {
    private final int productOptionId;
    private final int quantity;

  }

  @Getter
  @RequiredArgsConstructor
  @Builder
  public static class CartItemUpdateDto {
    private final int cartItemId;
    private final int quantity;
  }
}
