package com.example.kakao.domain.order;

import com.example.kakao._core.errors.exception.NotFoundException;
import com.example.kakao._core.errors.exception.UnAuthorizedException;
import com.example.kakao.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class OrderServiceTest {
  @Autowired
  private OrderService orderService;

  @MockBean
  private OrderJPARepository orderRepository;

  @Test
  @DisplayName("예외 - 주문내역 상세조회 - 존재하지 않는 주문")
  void findByIdWithNotFound() {
    // given
    int orderId = 0; // pk 가 0인 레코드는 존재할수없다.

    // when - then
    assertThrows(NotFoundException.class, () -> orderService.findById(orderId, 1));
  }

  @Test
  @DisplayName("예외 - 주문내역 상세조회 - 주문자가 아닌 유저가 조회시도")
  void findByIdWithUnAuthorized() {
    // given
    BDDMockito.given(orderRepository.findById(1)).willReturn(
      Optional.of(new Order(1, new User(2, "user2", "password", "name", "ROLE")))
    );

    // when - then
    assertThrows(UnAuthorizedException.class, () -> orderService.findById(1, 1));
  }
}