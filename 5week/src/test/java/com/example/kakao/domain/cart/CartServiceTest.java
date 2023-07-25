package com.example.kakao.domain.cart;

import com.example.kakao._core.errors.exception.BadRequestException;
import com.example.kakao.domain.cart.dto.request.SaveRequestDTO;
import com.example.kakao.domain.user.User;
import com.example.kakao.domain.user.UserJPARepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class CartServiceTest {
  @Autowired
  private CartService cartService;

  @Autowired
  private UserJPARepository userRepository;

  @Test
  @DisplayName("예외: 같은 옵션 2개 이상 담기 요청 시 예외처리")
  void addCartListSameOptionsError() {
    // given
    User user = userRepository.findByEmail("ssarmango@nate.com").get();
    List<SaveRequestDTO> requestDTOs = List.of(
      SaveRequestDTO.builder().optionId(1).quantity(2).build(),
      SaveRequestDTO.builder().optionId(1).quantity(4).build()
    );

    assertThrows(BadRequestException.class, () -> cartService.addCartList(requestDTOs, user));
  }


}