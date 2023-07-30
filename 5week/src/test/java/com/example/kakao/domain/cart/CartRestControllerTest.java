package com.example.kakao.domain.cart;

import com.example.kakao.docs.ApiDocUtil;
import com.example.kakao.domain.cart.dto.request.SaveRequestDTO;
import com.example.kakao.domain.cart.dto.request.UpdateRequestDTO;
import com.example.kakao.domain.cart.service.CartService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@WithUserDetails(value = "ssarmango@nate.com")
class CartRestControllerTest extends ApiDocUtil {

  @MockBean
  private CartService cartService;

  @Test
  void 성공_장바구니담기() throws Exception {
    // given
    List<SaveRequestDTO> requestDTOs = List.of(
      SaveRequestDTO.builder()
        .optionId(1)
        .quantity(3)
        .build(),
      SaveRequestDTO.builder()
        .optionId(2)
        .quantity(5)
        .build()
    );

    // stub
    willDoNothing().given(cartService).addCartList(BDDMockito.any(), BDDMockito.any());

    // when
    resultActions = mvc.perform(
      post("/carts/add")
        .contentType(MediaType.APPLICATION_JSON)
        .content(om.writeValueAsString(requestDTOs)));

    // then
    resultActions.andExpect(jsonPath("$.success").value("true"));
  }

  @Test
  void 실패_장바구니담기_수량이0일때() throws Exception {
    // given
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

    // stub
    willDoNothing().given(cartService).addCartList(BDDMockito.any(), BDDMockito.any());

    // when
    resultActions = mvc.perform(
      post("/carts/add")
        .contentType(MediaType.APPLICATION_JSON)
        .content(om.writeValueAsString(requestDTOs)));

    // then
    resultActions.andExpect(jsonPath("$.success").value("false"))
      .andExpect(jsonPath("$.error.status").value(400))
      .andExpect(jsonPath("$.error.message").value("Constraint Violation: [수량은 1 이상이어야 합니다.]"));
  }

  @Test
  void 성공_장바구니수정_수량이_0이면_삭제요청이다() throws Exception {
    // given
    List<UpdateRequestDTO> requestDTOs = List.of(
      UpdateRequestDTO.builder()
        .cartId(1)
        .quantity(0)
        .build()
    );

    // when
    given(cartService.update(BDDMockito.any(), BDDMockito.any()))
      .willReturn(null);

    resultActions = mvc.perform(
      post("/carts/update")
        .contentType(MediaType.APPLICATION_JSON)
        .content(om.writeValueAsString(requestDTOs)));

    // then
    resultActions.andExpect(jsonPath("$.success").value("true"));
  }

}