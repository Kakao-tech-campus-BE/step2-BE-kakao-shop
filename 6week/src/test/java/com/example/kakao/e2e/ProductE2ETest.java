package com.example.kakao.e2e;

import com.example.kakao.E2ETest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class ProductE2ETest extends E2ETest {

  @Nested
  class 전체상품목록조회 {
    @Test
    void 성공_전체상품목록조회() throws Exception {
      resultActions = mvc.perform(
        get("/products")
          .param("page", "0")
          .contentType(MediaType.APPLICATION_JSON));

      expectSuccess();
      resultActions
        .andExpect(jsonPath("$.response").isArray())
        .andExpect(jsonPath("$.response[0].id").value(1))
        .andExpect(jsonPath("$.response[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"))
        .andExpect(jsonPath("$.response[0].description").value(""))
        .andExpect(jsonPath("$.response[0].image").value("/images/1.jpg"))
        .andExpect(jsonPath("$.response[0].price").value(1000))
        .andExpect(jsonPath("$.response[1].id").value(2))
        .andExpect(jsonPath("$.response[1].productName").value("[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율"))
        .andExpect(jsonPath("$.response[1].description").value(""))
        .andExpect(jsonPath("$.response[1].image").value("/images/2.jpg"))
        .andExpect(jsonPath("$.response[1].price").value(2000));
    }

    @Test
    void 성공_전체상품목록조회_페이지가어있을때() throws Exception {
      resultActions = mvc.perform(
        get("/products")
          .param("page", "999")
          .contentType(MediaType.APPLICATION_JSON));

      expectSuccess();
    }

    @Test
    void 실패_전체상품목록조회_페이지번호가_음수일때() throws Exception {
      resultActions = mvc.perform(
        get("/products")
          .param("page", "-1")
          .contentType(MediaType.APPLICATION_JSON));

      expectFail(400);
    }

    @Test
    void 실패_전체상품목록조회_페이지번호가_숫자가_아닐때() throws Exception {
      resultActions = mvc.perform(
        get("/products")
          .param("page", "a")
          .contentType(MediaType.APPLICATION_JSON));

      expectFail(400);
    }

  }

  @Nested
  class 개별상품상세조회 {
    @Test
    void 성공_개별상품상세조회() throws Exception {
      resultActions = mvc.perform(
        get("/products/{id}", 1)
          .contentType(MediaType.APPLICATION_JSON));

      expectSuccess();
      resultActions
        .andExpect(jsonPath("$.response.id").value(1))
        .andExpect(jsonPath("$.response.productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"))
        .andExpect(jsonPath("$.response.description").value(""))
        .andExpect(jsonPath("$.response.image").value("/images/1.jpg"))
        .andExpect(jsonPath("$.response.price").value(1000));
    }

    @Test
    void 실패_개별상품상세조회_상품이_없을때() throws Exception {
      resultActions = mvc.perform(
        get("/products/{id}", 999)
          .contentType(MediaType.APPLICATION_JSON));

      expectFail(400);
    }

    @Test
    void 실패_개별상품상세조회_상품번호가_음수일때() throws Exception {
      resultActions = mvc.perform(
        get("/products/{id}", -1)
          .contentType(MediaType.APPLICATION_JSON));

      expectFail(400);
    }

    @Test
    void 실패_개별상품상세조회_상품번호가_숫자가_아닐때() throws Exception {
      resultActions = mvc.perform(
        get("/products/{id}", "a")
          .contentType(MediaType.APPLICATION_JSON));

      expectFail(400);
    }

  }
}
