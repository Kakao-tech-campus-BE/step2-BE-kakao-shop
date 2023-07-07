package com.example.kakaoshop.domain.cart;

import com.example.kakaoshop._core.security.CustomUserDetails;
import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.domain.cart.request.CartRequest;
import com.example.kakaoshop.domain.cart.response.CartItemDto;
import com.example.kakaoshop.domain.cart.response.CartRespFindAllDto;
import com.example.kakaoshop.domain.cart.response.ProductOptionDto;
import com.example.kakaoshop.domain.cart.response.ProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CartRestController {

  private final CartMockRepository cartRepository;

  private final CartService cartService;


  // 장바구니 조회
  @GetMapping("/cart")
  public ResponseEntity<Object> findAll(@AuthenticationPrincipal CustomUserDetails userInfo) {
    List<CartItem> cartItemList = cartRepository.findAllByUserId(userInfo.getId());

    List<CartItemDto> cartItemDtoList = cartService.buildCartItemDtoList(cartItemList);

    // productDTO 리스트 만들기
    List<ProductDto> productDtoList = new ArrayList<>();

    // productDTO 리스트에 담기
    productDtoList.add(
      ProductDto.builder()
        .id(1)
        .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
        .cartItems(cartItemDtoList)
        .build()
    );

    CartRespFindAllDto responseDTO = new CartRespFindAllDto(productDtoList, 104500);

    return ResponseEntity.ok(ApiUtils.success(responseDTO));
  }

  // 장바구니 담기
  @PostMapping("/cart/items")
  public ResponseEntity<Object> addCartItem(
    @AuthenticationPrincipal CustomUserDetails userInfo,
    @RequestBody CartRequest.CartItemAddDto cartItemAddDto
  ) {
    // Mock Insert
    // TODO:    상품 가격 확인, 동일한 옵션 추가 수량 증가처리

    List<CartItem> cartItemList = cartRepository.findAllByUserId(userInfo.getId());

    if (!cartItemList.isEmpty()) cartItemList.clear(); // debug

    List<CartItemDto> cartItemDtoList = new ArrayList<>();

    // 카트 아이템 리스트에 담기
    CartItemDto cartItemDto1 = CartItemDto.builder()
      .id(4)
      .quantity(5)
      .price(50000)
      .build();
    cartItemDto1.setOption(ProductOptionDto.builder()
      .id(1)
      .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
      .price(10000)
      .build());
    cartItemDtoList.add(cartItemDto1);

    CartItemDto cartItemDto2 = CartItemDto.builder()
      .id(5)
      .quantity(5)
      .price(54500)
      .build();
    cartItemDto2.setOption(ProductOptionDto.builder()
      .id(1)
      .optionName("02. 슬라이딩 지퍼백 크리스마스에디션 5종")
      .price(10900)
      .build());
    cartItemDtoList.add(cartItemDto2);


    return ResponseEntity.ok(ApiUtils.success());
  }

  /**
   * 장바구니 옵션 수량 변경
   * + 또는 - 버튼 하나를 누를때마다, 해당 옵션의 수량이 변경됨.
   * 추후 +,- 버튼 이외에 직접 숫자를 입력하는 경우도 있을수 있으므로, 입력값에서 수량을 숫자로 받는다.
   * 수량이 0이 되면, 장바구니에서 삭제됨.
   */
  @PatchMapping("/carts")
  public ResponseEntity<Object> updateCartItem(
    @AuthenticationPrincipal CustomUserDetails userInfo,
    @RequestBody CartRequest.CartItemUpdateDto cartItemUpdateDto) {

    // Mock Update

    // 1. 장바구니에서 해당 옵션 찾기 -> 없으면 예외처리
    // 2. 수량 변경
    // 3. 수량이 0이면 장바구니에서 삭제

    return ResponseEntity.ok(ApiUtils.success());
  }

  // 장바구니 옵션 1개 삭제
  @DeleteMapping("/carts/{cart-item-id}")
  public ResponseEntity<Object> deleteCartItem(
    @AuthenticationPrincipal CustomUserDetails userInfo,
    @PathVariable("cart-item-id") int cartItemId) {
    cartRepository.deleteByIdAndAccountId(cartItemId, userInfo.getId());

    return ResponseEntity.ok(ApiUtils.success());
  }

  // 장바구니 전체 비우기
  @DeleteMapping("/carts")
  public ResponseEntity<Object> deleteCart(@RequestParam("cart-item-id") int cartItemId) {

    // Mock Delete
    cartRepository.clearMockCartData();

    return ResponseEntity.ok(ApiUtils.success());
  }

}
