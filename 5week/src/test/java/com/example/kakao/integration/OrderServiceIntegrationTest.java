package com.example.kakao.integration;

import com.example.kakao.domain.cart.service.CartService;
import com.example.kakao.domain.cart.dto.request.SaveRequestDTO;
import com.example.kakao.domain.cart.dto.response.FindAllResponseDTO;
import com.example.kakao.domain.order.OrderService;
import com.example.kakao.domain.order.dto.OrderDetailResponseDTO;
import com.example.kakao.domain.user.User;
import com.example.kakao.domain.user.UserJPARepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class OrderServiceIntegrationTest {
  @Autowired
  private UserJPARepository userRepository;

  @Autowired
  private CartService cartService;

  @Autowired
  private OrderService orderService;

  @Test
  @DisplayName("주문하기 - 주문 상세 조회")
  void order() throws JsonProcessingException {
    // given
    User user = userRepository.findByEmail("ssarmango@nate.com").get();
    List<SaveRequestDTO> requestDTOs = List.of(
      SaveRequestDTO.builder().optionId(1).quantity(3).build(),
      SaveRequestDTO.builder().optionId(2).quantity(2).build(),
      SaveRequestDTO.builder().optionId(6).quantity(2).build(),
      SaveRequestDTO.builder().optionId(7).quantity(2).build(),
      SaveRequestDTO.builder().optionId(9).quantity(2).build(),
      SaveRequestDTO.builder().optionId(10).quantity(2).build()
    );
    cartService.addCartList(requestDTOs, user);

    // when
    OrderDetailResponseDTO savedResponseDTO = orderService.save(user.getId());
    OrderDetailResponseDTO findResponseDTO = orderService.findById(savedResponseDTO.getId(), user.getId());

    // then
    // product 개수 확인
    assertThat(findResponseDTO.getProducts()).hasSize(3);

    // 수량 합 확인
    assertThat(findResponseDTO.getProducts().stream()
      .map(product -> product.getItems().stream()
        .map(OrderDetailResponseDTO.ItemDTO::getQuantity)
        .reduce(0, Integer::sum)
    ).reduce(0, Integer::sum)).isEqualTo(13);

    // 가격 합 확인
    assertThat(findResponseDTO.getProducts().stream()
      .map(product -> product.getItems().stream()
        .map(OrderDetailResponseDTO.ItemDTO::getPrice)
        .reduce(0L, Long::sum)
      ).reduce(0L, Long::sum)).isEqualTo(
        30000 + 21800 + 19800 + 29000 + 59800 + 99800
    );
  }

  @Test
  @DisplayName("주문 이후 장바구니 초기화 확인")
  void cartResetAfterOrder() {
    // given
    User user = userRepository.findByEmail("ssarmango@nate.com").get();
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
