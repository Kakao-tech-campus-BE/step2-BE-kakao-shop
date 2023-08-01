package com.example.kakao.integration;

import com.example.kakao.IntegrationTest;
import com.example.kakao.domain.cart.dto.request.SaveRequestDTO;
import com.example.kakao.domain.cart.dto.response.FindAllResponseDTO;
import com.example.kakao.domain.cart.service.CartService;
import com.example.kakao.domain.order.OrderService;
import com.example.kakao.domain.user.User;
import com.example.kakao.domain.user.UserJPARepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class OrderServiceIntegrationTest extends IntegrationTest {
  @Autowired
  private UserJPARepository userRepository;

  @Autowired
  private CartService cartService;

  @Autowired
  private OrderService orderService;

  @Test
  @DisplayName("주문 이후 장바구니 초기화 확인")
  void cartResetAfterOrder() {
    // given
    User user = userRepository.findByEmail("ssar@nate.com").get();
    List<SaveRequestDTO> requestDTOs = List.of(
      SaveRequestDTO.builder().optionId(1).quantity(3).build(),
      SaveRequestDTO.builder().optionId(2).quantity(2).build()
    );

    // when
    cartService.addCartList(requestDTOs, user); // 장바구니 담기
    orderService.save(user.getId()); // 주문
    FindAllResponseDTO cartAfterOrder = cartService.findAll(user); // 주문 이후 장바구니 조회

    // then
    assertThat(cartAfterOrder.getProducts()).isEmpty();
    assertThat(cartAfterOrder.getTotalPrice()).isZero();
  }


}
