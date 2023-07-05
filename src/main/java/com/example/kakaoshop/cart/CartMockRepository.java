package com.example.kakaoshop.cart;

import com.example.kakaoshop.cart.response.CartItemDTO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CartMockRepository {

  // Mock DB Data
  // CartItem(ProductOption + Quantity) List 가 실제 DB에 저장되고,
  // 조회 시 Product 를 Join 해서 JSON 형식으로 반환해주는 방식.
  private List<CartItemDTO> cartItemDTOList = new ArrayList<>();

  public void clearMockCartData() {
    cartItemDTOList.clear();
  }

  public List<CartItemDTO> findAllByUserId() {
    return cartItemDTOList;
  }


  public void save(List<CartItemDTO> cartItemDTOList) {
    this.cartItemDTOList = cartItemDTOList;
  }
}
