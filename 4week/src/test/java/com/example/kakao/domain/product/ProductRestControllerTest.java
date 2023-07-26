package com.example.kakao.domain.product;

import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.domain.product.option.Option;
import com.example.kakao.log.ErrorLogJPARepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@Import({SecurityConfig.class})
@WebMvcTest(controllers = {ProductRestController.class})
class ProductRestControllerTest {
  @MockBean
  FakeStore fakeStore;

  @MockBean
  private ErrorLogJPARepository errorLogJPARepository;

  @Autowired
  MockMvc mvc;


  @Test
  @DisplayName("상품 목록 조회")
  void findAll() throws Exception {
    // stub
    BDDMockito.given(fakeStore.getProductList()).willReturn(Arrays.asList(
      new Product(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "description", "/image/path", 1000),
      new Product(2, "[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율", "description", "/image/path", 2000),
      new Product(3, "삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!", "description", "/image/path", 30000)
    ));

    // when
    ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.get("/products"));

    // then
    resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response").isArray());
    resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.length()").value(3));
    resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response[0].id").value(1));
    resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response[2].id").value(3));
  }

  @Test
  @DisplayName("상품 상세 조회")
  void findById() throws Exception {
    // given
    Product product = new Product(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "description", "/image/path", 1000);

    // stub
    BDDMockito.given(fakeStore.getProductList()).willReturn(Arrays.asList(
      product
    ));

    BDDMockito.given(fakeStore.getOptionList()).willReturn(Arrays.asList(
      new Option(1, product, "지퍼백1", 5000),
      new Option(2, product, "주방용품1", 8000),
      new Option(3, product, "주방용품2", 9000)
    ));

    // when
    ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.get("/products/1"));

    // then
    resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response").isMap());
    resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(1));
    resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
    resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[0].id").value(1));
    resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[0].optionName").value("지퍼백1"));
    resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[2].id").value(3));
    resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.response.options[2].optionName").value("주방용품2"));
  }
}