package com.example.kakaoshop.cart;

import com.example.kakaoshop._core.security.CustomUserDetails;
import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.cart.response.CartItemDTO;
import com.example.kakaoshop.cart.response.CartRespFindAllDTO;
import com.example.kakaoshop.cart.response.ProductOptionDTO;
import com.example.kakaoshop.cart.response.ProductDTO;
import com.example.kakaoshop.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CartRestController {

  private final CartMockRepository cartRepository;


  // 장바구니 조회
  @GetMapping("/cart")
  public ResponseEntity<?> findAll(@AuthenticationPrincipal CustomUserDetails userInfo) {
    List<CartItemDTO> cartItemDTOList = cartRepository.findAllByUserId(userInfo.getId());

    // productDTO 리스트 만들기
    List<ProductDTO> productDTOList = new ArrayList<>();

    // productDTO 리스트에 담기
    productDTOList.add(
      ProductDTO.builder()
        .id(1)
        .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
        .cartItems(cartItemDTOList)
        .build()
    );

    CartRespFindAllDTO responseDTO = new CartRespFindAllDTO(productDTOList, 104500);


    return ResponseEntity.ok(ApiUtils.success(responseDTO));
  }

  // 장바구니 담기
  @PostMapping("/cart-item")
  public ResponseEntity<?> addCartItem(
    @AuthenticationPrincipal CustomUserDetails userInfo,
    @RequestParam("product-option-id") int productOptionId,
    @RequestParam("quantity") int quantity) {
    // Mock Insert
    // TODO:    상품 가격 확인, 동일한 옵션 추가 수량 증가처리
    //

    List<CartItemDTO> cartItemDTOList = cartRepository.findAllByUserId(userInfo.getId());

    if (!cartItemDTOList.isEmpty()) cartItemDTOList.clear();

    // 카트 아이템 리스트에 담기
    CartItemDTO cartItemDTO1 = CartItemDTO.builder()
      .id(4)
      .quantity(5)
      .price(50000)
      .build();
    cartItemDTO1.setOption(ProductOptionDTO.builder()
      .id(1)
      .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
      .price(10000)
      .build());
    cartItemDTOList.add(cartItemDTO1);

    CartItemDTO cartItemDTO2 = CartItemDTO.builder()
      .id(5)
      .quantity(5)
      .price(54500)
      .build();
    cartItemDTO2.setOption(ProductOptionDTO.builder()
      .id(1)
      .optionName("02. 슬라이딩 지퍼백 크리스마스에디션 5종")
      .price(10900)
      .build());
    cartItemDTOList.add(cartItemDTO2);

    cartRepository.save(cartItemDTOList);

    return ResponseEntity.ok(ApiUtils.success(null));
  }

  /**
   * 장바구니 옵션 수량 변경
   * + 또는 - 버튼 하나를 누를때마다, 해당 옵션의 수량이 변경됨.
   * 추후 +,- 버튼 이외에 직접 숫자를 입력하는 경우도 있을수 있으므로, 입력값에서 수량을 숫자로 받는다.
   * 수량이 0이 되면, 장바구니에서 삭제됨.
   */
  @PatchMapping("/carts")
  public ResponseEntity<?> updateCartItem(@RequestParam("cart-item-id") int cartItemId,
                                          @RequestParam("quantity") int quantity) {

    // Mock Update

    // 1. 장바구니에서 해당 옵션 찾기 -> 없으면 예외처리
    // 2. 수량 변경
    // 3. 수량이 0이면 장바구니에서 삭제

    return ResponseEntity.ok(ApiUtils.success(null));
  }

  // 장바구니 옵션 1개 삭제
  @DeleteMapping("/carts/{cart-item-id}")
  public ResponseEntity<?> deleteCartItem(@AuthenticationPrincipal CustomUserDetails userInfo,
                                          @PathVariable("cart-item-id") int cartItemId) {
    List<CartItemDTO> cartItemDTOList = cartRepository.findAllByUserId(userInfo.getId());

    // Mock Delete
    cartItemDTOList.removeIf(cartItemDTO -> cartItemDTO.getId() == cartItemId);

    cartRepository.save(cartItemDTOList);

    return ResponseEntity.ok(ApiUtils.success(null));
  }

  // 장바구니 전체 비우기
  @DeleteMapping("/carts")
  public ResponseEntity<?> deleteCart(@RequestParam("cart-item-id") int cartItemId) {

    // Mock Delete
    cartRepository.clearMockCartData();

    return ResponseEntity.ok(ApiUtils.success(null));
  }

}
