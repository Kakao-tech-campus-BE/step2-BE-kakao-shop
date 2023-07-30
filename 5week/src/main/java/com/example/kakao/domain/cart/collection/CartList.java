package com.example.kakao.domain.cart.collection;

import com.example.kakao._core.errors.exception.BadRequestException;
import com.example.kakao.domain.cart.Cart;
import com.example.kakao.domain.cart.dto.request.UpdateRequestDTO;
import lombok.Getter;

import java.util.List;
import java.util.function.Predicate;

/**
 * 비어있는 장바구니를 생성하려고 하면 예외 발생
 */
@Getter
public final class CartList {
  private final List<Cart> items;

  public CartList(List<Cart> items) {
    if (items.isEmpty()) throw new BadRequestException("장바구니에 담긴 상품이 없습니다.");
    this.items = items;
  }

  public boolean isEmpty() {
    return items.isEmpty();
  }

  public void remove(Cart cartInRequest) {
    items.remove(cartInRequest);
  }

  /**
   * 요청 받은 아이템들이 장바구니에 이미 존재하면 예외 발생
   */
  public Cart findCartInRequest(UpdateRequestDTO updateDTO) {
    return items.stream().filter( isInList(updateDTO) ).findFirst()
      .orElseThrow(() -> new BadRequestException("해당하는 CartId 가 장바구니에 존재하지 않습니다. : " + updateDTO.getCartId()));
      // Lazy Validation
  }

  private Predicate<Cart> isInList(UpdateRequestDTO updateDTO) {
    return cart -> cart.getId() == updateDTO.getCartId();
  }

}
