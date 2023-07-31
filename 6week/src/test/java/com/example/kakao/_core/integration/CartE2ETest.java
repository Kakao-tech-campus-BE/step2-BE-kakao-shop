package com.example.kakao._core.integration;

import com.example.kakao._core.E2ETest;
import com.example.kakao.domain.cart.dto.request.SaveRequestDTO;
import com.example.kakao.domain.cart.dto.request.UpdateRequestDTO;
import com.example.kakao.domain.cart.service.CartPolicyManager;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


class CartE2ETest extends E2ETest {

  @Nested
  class 장바구니담기 {
    @Test
    void 성공_장바구니담기() throws Exception {
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

      resultActions = mvc.perform(
        post("/carts/add")
          .contentType(MediaType.APPLICATION_JSON)
          .content(om.writeValueAsString(requestDTOs)));

      expectSuccess();
    }

    @Test
    void 실패_장바구니담기_수량이0일때() throws Exception {
      List<SaveRequestDTO> requestDTOs = List.of(
        SaveRequestDTO.builder()
          .optionId(1)
          .quantity(8)
          .build(),
        SaveRequestDTO.builder()
          .optionId(3)
          .quantity(0)
          .build()
      );

      resultActions = mvc.perform(
        post("/carts/add")
          .contentType(MediaType.APPLICATION_JSON)
          .content(om.writeValueAsString(requestDTOs)));

      expectFail("Constraint Violation: [수량은 1 이상이어야 합니다.]", 400);
    }
  }

  @Nested
  class 장바구니조회 {
    @Test
    void 성공_장바구니조회() throws Exception {
      resultActions = mvc.perform(
        get("/carts/"));

      expectSuccess();
      resultActions
        .andExpect(jsonPath("$.response.products[0].id").value(1))
        .andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"))
        .andExpect(jsonPath("$.response.products[0].carts[0].id").value(1))
        .andExpect(jsonPath("$.response.products[0].carts[0].option.id").value(1))
        .andExpect(jsonPath("$.response.products[0].carts[0].option.optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"))
        .andExpect(jsonPath("$.response.products[0].carts[0].quantity").value(5))
        .andExpect(jsonPath("$.response.products[0].carts[0].price").value(50000))
        .andExpect(jsonPath("$.response.products[0].carts[1].id").value(2))
        .andExpect(jsonPath("$.response.products[0].carts[1].option.id").value(2))
        .andExpect(jsonPath("$.response.products[0].carts[1].option.optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"))
        .andExpect(jsonPath("$.response.products[0].carts[1].quantity").value(1))
        .andExpect(jsonPath("$.response.products[0].carts[1].price").value(10900))
        .andExpect(jsonPath("$.response.products[1].id").value(4))
        .andExpect(jsonPath("$.response.products[1].productName").value("바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종"))
        .andExpect(jsonPath("$.response.products[1].carts[0].id").value(3))
        .andExpect(jsonPath("$.response.products[1].carts[0].option.id").value(16))
        .andExpect(jsonPath("$.response.products[1].carts[0].option.optionName").value("선택02_바른곡물효소누룽지맛 6박스"))
        .andExpect(jsonPath("$.response.products[1].carts[0].quantity").value(5))
        .andExpect(jsonPath("$.response.products[1].carts[0].price").value(250000))
        .andExpect(jsonPath("$.response.totalPrice").value(310900));

    }
  }

  @Nested
  class 장바구니수정 {
    @Test
    void 성공_장바구니수정_수량이_0이면_삭제요청이다() throws Exception {
      List<UpdateRequestDTO> requestDTOs = List.of(
        UpdateRequestDTO.builder()
          .cartId(1)
          .quantity(0)
          .build(),
        UpdateRequestDTO.builder()
          .cartId(2)
          .quantity(3)
          .build()
      );

      resultActions = mvc.perform(
        post("/carts/update")
          .contentType(MediaType.APPLICATION_JSON)
          .content(om.writeValueAsString(requestDTOs)));

      expectSuccess();
      resultActions.andExpect(jsonPath("$.response.carts[0].cartId").value(2))
        .andExpect(jsonPath("$.response.carts[0].optionId").value(2))
        .andExpect(jsonPath("$.response.carts[0].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"))
        .andExpect(jsonPath("$.response.carts[0].quantity").value(3))
        .andExpect(jsonPath("$.response.carts[0].price").value(32700))
        .andExpect(jsonPath("$.response.carts[1].cartId").value(3))
        .andExpect(jsonPath("$.response.carts[1].optionId").value(16))
        .andExpect(jsonPath("$.response.carts[1].optionName").value("선택02_바른곡물효소누룽지맛 6박스"))
        .andExpect(jsonPath("$.response.carts[1].quantity").value(5))
        .andExpect(jsonPath("$.response.carts[1].price").value(250000))
        .andExpect(jsonPath("$.response.totalPrice").value(282700));
    }

    @Test
    void 실패_장바구니수정_장바구니에없는품목을수정하려고시도한다() throws Exception {
      int notExistCartId = 99;

      List<UpdateRequestDTO> requestDTOs = List.of(
        UpdateRequestDTO.builder()
          .cartId(1)
          .quantity(0)
          .build(),
        UpdateRequestDTO.builder()
          .cartId(notExistCartId)
          .quantity(3)
          .build()
      );

      resultActions = mvc.perform(
        post("/carts/update")
          .contentType(MediaType.APPLICATION_JSON)
          .content(om.writeValueAsString(requestDTOs)));

      expectFail("해당하는 CartId 가 장바구니에 존재하지 않습니다. : " + notExistCartId, 400);
    }

    @Test
    void 실패_장바구니수정_요청가능한_최대수량초과() throws Exception {
      int notExistCartId = 99;

      List<UpdateRequestDTO> requestDTOs = List.of(
        UpdateRequestDTO.builder()
          .cartId(1)
          .quantity(CartPolicyManager.MAX_QUANTITY + 1)
          .build()
      );

      resultActions = mvc.perform(
        post("/carts/update")
          .contentType(MediaType.APPLICATION_JSON)
          .content(om.writeValueAsString(requestDTOs)));

      expectFail(400);
    }

    @Test
    void 실패_장바구니수정_요청에중복ID가포함되어있다() throws Exception {
      int duplicatedCartId = 1;

      List<UpdateRequestDTO> requestDTOs = List.of(
        UpdateRequestDTO.builder()
          .cartId(1)
          .quantity(1)
          .build(),
        UpdateRequestDTO.builder()
          .cartId(1)
          .quantity(1)
          .build()
      );

      resultActions = mvc.perform(
        post("/carts/update")
          .contentType(MediaType.APPLICATION_JSON)
          .content(om.writeValueAsString(requestDTOs)));

      expectFail(400);
    }
  }

}
