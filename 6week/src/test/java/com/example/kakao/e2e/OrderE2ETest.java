package com.example.kakao.e2e;

import com.example.kakao.E2ETest;
import com.example.kakao.domain.cart.CartJPARepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class OrderE2ETest extends E2ETest {
  @SpyBean
  private CartJPARepository cartRepository;

  @Nested
  class 결제하기 {
    @Test
    void 성공_결제하기() throws Exception {
      resultActions = mvc.perform(
        post("/orders/save")
          .contentType(MediaType.APPLICATION_JSON));

      expectSuccess();
      resultActions
        .andExpect(jsonPath("$.response.id").value(2))
        .andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"))
        .andExpect(jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"))
        .andExpect(jsonPath("$.response.products[0].items[0].quantity").value(5))
        .andExpect(jsonPath("$.response.products[0].items[0].price").value(50000))
        .andExpect(jsonPath("$.response.products[0].items[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"))
        .andExpect(jsonPath("$.response.products[0].items[1].quantity").value(1))
        .andExpect(jsonPath("$.response.products[0].items[1].price").value(10900))
        .andExpect(jsonPath("$.response.products[1].productName").value("바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종"))
        .andExpect(jsonPath("$.response.products[1].items[0].optionName").value("선택02_바른곡물효소누룽지맛 6박스"))
        .andExpect(jsonPath("$.response.products[1].items[0].quantity").value(5))
        .andExpect(jsonPath("$.response.products[1].items[0].price").value(250000))
        .andExpect(jsonPath("$.response.totalPrice").value(310900));
    }

    @Test
    void 실패_결제하기_장바구니비어있음() throws Exception {
      given(cartRepository.findAllByUserId(anyInt()))
        .willReturn(List.of());

      resultActions = mvc.perform(
        post("/orders/save")
          .contentType(MediaType.APPLICATION_JSON));

      expectFail("장바구니에 담긴 상품이 없습니다.", 400);
    }
  }

  @Nested
  class 주문결과확인 {
    @Test
    void 성공_주문결과확인() throws Exception {
      resultActions = mvc.perform(
        get("/orders/{id}", 1)
          .contentType(MediaType.APPLICATION_JSON));

      expectSuccess();
      resultActions
        .andExpect(jsonPath("$.response.id").value(1))
        .andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"))
        .andExpect(jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"))
        .andExpect(jsonPath("$.response.products[0].items[0].quantity").value(5))
        .andExpect(jsonPath("$.response.products[0].items[0].price").value(50000))
        .andExpect(jsonPath("$.response.products[0].items[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"))
        .andExpect(jsonPath("$.response.products[0].items[1].quantity").value(1))
        .andExpect(jsonPath("$.response.products[0].items[1].price").value(10900))
        .andExpect(jsonPath("$.response.products[1].productName").value("바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종"))
        .andExpect(jsonPath("$.response.products[1].items[0].optionName").value("선택02_바른곡물효소누룽지맛 6박스"))
        .andExpect(jsonPath("$.response.products[1].items[0].quantity").value(5))
        .andExpect(jsonPath("$.response.products[1].items[0].price").value(250000))
        .andExpect(jsonPath("$.response.totalPrice").value(310900));
    }

    @Test
    void 실패_주문결과확인_주문내역이존재하지않음() throws Exception {
      int notExistId = 99;

      resultActions = mvc.perform(
        get("/orders/{id}", notExistId)
          .contentType(MediaType.APPLICATION_JSON));

      expectFail("주문 내역을 찾을 수 없습니다. : " + notExistId, 404);
    }

  }

}
