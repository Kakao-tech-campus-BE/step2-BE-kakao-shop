package com.example.kakao.domain.cart;

import com.example.kakao._core.errors.exception.BadRequestException;
import com.example.kakao.domain.product.option.OptionJPARepository;
import com.example.kakao.domain.user.User;
import com.example.kakao.domain.user.UserJPARepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class CartServiceTest {
  @Autowired
  private CartService cartService;

  @MockBean
  private CartJPARepository cartRepository;

  @MockBean
  private OptionJPARepository optionRepository;

  @Autowired
  private UserJPARepository userRepository;

  @Test
  @DisplayName("예외: 같은 옵션 2개 이상 담기 요청 시 예외처리")
  void addCartListSameOptionsError() {
    // given
    User user = userRepository.findByEmail("ssarmango@nate.com").get();
    List<CartRequest.SaveDTO> requestDTOs = List.of(
      CartRequest.SaveDTO.builder().optionId(1).quantity(2).build(),
      CartRequest.SaveDTO.builder().optionId(1).quantity(4).build()
    );

    assertThrows(BadRequestException.class, () -> cartService.addCartList(requestDTOs, user));
  }

}