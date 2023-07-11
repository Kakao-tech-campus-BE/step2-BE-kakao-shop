package com.example.kakaoshop.domain.cart;

import com.example.kakaoshop.domain.cart.response.CartItemDto;
import com.example.kakaoshop.domain.cart.response.ProductOptionDto;
import com.example.kakaoshop.domain.product.option.ProductOption;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

  public List<CartItemDto> buildCartItemDtoList(List<CartItem> cartItemList) {
    return cartItemList.stream()
      .map(cartItem -> CartItemDto.builder()
        .id(cartItem.getId())
        .option(buildProductOptionDto(cartItem.getProductOption()))
        .quantity(cartItem.getQuantity())
        .build())
      .collect(Collectors.toList());
  }


  private ProductOptionDto buildProductOptionDto(ProductOption productOption) {
    return ProductOptionDto.builder()
      .id(productOption.getId())
      .optionName(productOption.getName())
      .price(productOption.getPrice())
      .build();
  }
}
