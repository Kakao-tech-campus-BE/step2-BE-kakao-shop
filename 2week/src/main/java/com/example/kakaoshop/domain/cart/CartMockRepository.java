package com.example.kakaoshop.domain.cart;

import com.example.kakaoshop.domain.cart.response.CartItemDto;
import com.example.kakaoshop.domain.cart.response.ProductOptionDto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CartMockRepository {

  // Mock DB Data

//  CartItemDto cartItemDto1 = CartItemDto.builder()
//    .id(4)
//    .quantity(5)
//    .price(50000)
//    .build();
//    cartItemDto1.setOption(ProductOptionDto.builder()
//      .id(1)
//      .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
//      .price(10000)
//      .build());
//    cartItemDtoList.add(cartItemDto1);
//
//  CartItemDto cartItemDto2 = CartItemDto.builder()
//    .id(5)
//    .quantity(5)
//    .price(54500)
//    .build();
//    cartItemDto2.setOption(ProductOptionDto.builder()
//      .id(1)
//      .optionName("02. 슬라이딩 지퍼백 크리스마스에디션 5종")
//      .price(10900)
//      .build());
//    cartItemDtoList.add(cartItemDto2);
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
