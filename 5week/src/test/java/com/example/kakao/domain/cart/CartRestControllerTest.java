package com.example.kakao.domain.cart;

import com.example.kakao._core.util.DummyJwt;
import com.example.kakao.domain.cart.dto.request.SaveRequestDTO;
import com.example.kakao.log.ErrorLogJPARepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class CartRestControllerTest {
  @Autowired
  MockMvc mvc;

  @MockBean
  private CartService cartService;

  @MockBean
  private ErrorLogJPARepository errorLogJPARepository;

  @Autowired
  private ObjectMapper om;

  @Test
  @DisplayName("예외 - 장바구니 담기 - 수량이 0일 때")
  void addCartListWithZeroQuantity() throws Exception {
    // given
    String token = DummyJwt.generate();
    List<SaveRequestDTO> requestDTOs = List.of(
      SaveRequestDTO.builder()
        .optionId(1)
        .quantity(0)
        .build(),
      SaveRequestDTO.builder()
        .optionId(2)
        .quantity(5)
        .build(),
      SaveRequestDTO.builder()
        .optionId(3)
        .quantity(5)
        .build()
    );

    // when
    ResultActions resultActions = mvc.perform(
      post("/carts/add")
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON)
        .content(om.writeValueAsString(requestDTOs)))
        .andDo(print());

    // then
    resultActions.andExpect(jsonPath("$.success").value("false"));
    resultActions.andExpect(jsonPath("$.error.status").value(400));
    resultActions.andExpect(jsonPath("$.error.message").value("Constraint Violation: [수량은 1 이상이어야 합니다.]"));
  }

}