package com.example.kakao.integration;

import com.example.kakao.IntegrationTest;
import com.example.kakao._core.errors.exception.BadRequestException;
import com.example.kakao.domain.cart.Cart;
import com.example.kakao.domain.cart.CartJPARepository;
import com.example.kakao.domain.cart.dto.request.SaveRequestDTO;
import com.example.kakao.domain.cart.dto.request.UpdateRequestDTO;
import com.example.kakao.domain.cart.dto.response.UpdateResponseDTO;
import com.example.kakao.domain.cart.service.CartService;
import com.example.kakao.domain.product.option.OptionJPARepository;
import com.example.kakao.domain.user.User;
import com.example.kakao.domain.user.UserJPARepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


class CartServiceIntegrationTest extends IntegrationTest {
  @Autowired
  private CartService cartService;

  @Autowired
  private CartJPARepository cartRepository;

  @Autowired
  private OptionJPARepository optionRepository;

  @Autowired
  private UserJPARepository userRepository;

  private User user;

  @BeforeEach
  void setUpUser() {
    user = userRepository.findByEmail("ssarmango@nate.com").get();
  }


  @Test
  @DisplayName("장바구니에 상품 담기 - 이미 존재하는 상품은 수량, 가격만 업데이트")
  void addCartListWithUpdate() {
    // given
    List<SaveRequestDTO> requestDTOs = List.of(
      SaveRequestDTO.builder().optionId(1).quantity(3).build(),
      SaveRequestDTO.builder().optionId(2).quantity(2).build(),
      SaveRequestDTO.builder().optionId(4).quantity(2).build()
    );

    // when
    cartService.addCartList(requestDTOs, user);
    List<Cart> cartList = cartRepository.findAllByUserId(user.getId());

    // then
    assertThat(cartList).hasSize(4);
    assertThat(cartList.get(0).getQuantity()).isEqualTo(8);
    assertThat(cartList.get(0).getPrice()).isEqualTo(10000 * 8);
    assertThat(cartList.get(1).getQuantity()).isEqualTo(3);
    assertThat(cartList.get(2).getQuantity()).isEqualTo(5);
    assertThat(cartList.get(3).getQuantity()).isEqualTo(2);
  }


  @Test
  void 장바구니_수정() {
    // given
    List<UpdateRequestDTO> updateDTOs = List.of(
      UpdateRequestDTO.builder().cartId(1).quantity(0).build(), // 삭제
      UpdateRequestDTO.builder().cartId(2).quantity(4).build()
    );

    // when
    UpdateResponseDTO responseDTO = cartService.update(updateDTOs, user);
    List<Cart> cartListInDB = cartRepository.findAllByUserId(user.getId());

    // then
    // DB 검증
    assertThat(cartListInDB).hasSize(2);
    assertThat(cartListInDB.get(0).getQuantity()).isEqualTo(4); // -- 올바르게 수량이 변경되었는가
    assertThat(cartListInDB.get(1).getOption().getId()).isEqualTo(16); // -- 명시하지 않은 요소는 그대로 유지되는가 ?
    assertThat(cartListInDB.get(1).getQuantity()).isEqualTo(5);

    // 응답 검증
    assertThat(responseDTO.getCarts()).hasSize(2);
    assertThat(responseDTO.getCarts().get(0).getQuantity()).isEqualTo(4);
    assertThat(responseDTO.getCarts().get(1).getQuantity()).isEqualTo(5); // 요청에 들어있지 않은 요소는 그대로 유지
  }

  @Test
  @DisplayName("예외: 장바구니 수정요청 - 유저의 장바구니에 들어있지 않은 Id가 수정요청에 포함된 경우")
  void updateCartWithInvalidCartId() {
    // given
    int invalidCartId = 99;
    List<UpdateRequestDTO> updateDTOs = List.of(
      UpdateRequestDTO.builder().cartId(1).quantity(2).build(),
      UpdateRequestDTO.builder().cartId(2).quantity(1).build(),
      UpdateRequestDTO.builder().cartId(invalidCartId).quantity(1).build()
    );

    // when - then
    BadRequestException exception =
      assertThrows(BadRequestException.class, () -> cartService.update(updateDTOs, user));
    assertThat(exception.getMessage()).isEqualTo("해당하는 CartId 가 장바구니에 존재하지 않습니다. : " + invalidCartId);

  }
}
