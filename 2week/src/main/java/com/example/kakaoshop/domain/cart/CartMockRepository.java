package com.example.kakaoshop.domain.cart;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartMockRepository {

  private List<CartItem> cartItemList = List.of(
    CartItem.builder()
      .quantity(5)
      .price(50000)
      .build(),
    CartItem.builder()
      .quantity(5)
      .price(54500)
      .build()
  );


  public void clearMockCartData() {
    cartItemList.clear();
  }

  public List<CartItem> findAllByUserId(int userId) {
    // return mock data
    return cartItemList;
  }

  public void save(List<CartItem> cartItemList) {
    this.cartItemList = cartItemList;
  }

  public void deleteByIdAndAccountId(int cartItemId, int userId) {
    // mock delete
    cartItemList.removeIf(cartItem -> cartItem.getId() == cartItemId && cartItem.getAccount().getId() == userId);
  }
}
