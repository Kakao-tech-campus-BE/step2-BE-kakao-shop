package com.example.kakao.product;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.util.DummyEntity;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.product.option.Option;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@Import({
    SecurityConfig.class,
    GlobalExceptionHandler.class
})
@WebMvcTest(controllers = {ProductRestController.class})
public class ProductRestControllerTest extends DummyEntity {

  @MockBean
  FakeStore fakeStore;

  @MockBean
  private ErrorLogJPARepository errorLogJPARepository;

  @Autowired
  MockMvc mvc;

  @Autowired
  private ObjectMapper om;


  @Test
  @DisplayName("전체 상품 목록 조회 테스트")
  public void product_findAll_test() throws Exception {
    // given
    List<Product> products = productDummyList();

    //stub
    Mockito.when(fakeStore.getProductList()).thenReturn(products);

    // when
    ResultActions result = mvc.perform(
        MockMvcRequestBuilders
            .get("/products")
            .contentType(MediaType.APPLICATION_JSON)
    );

    String responseBody = result.andReturn().getResponse().getContentAsString();
    System.out.println("테스트 : " + responseBody);

    // then
    result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));

  }

  @Test
  @DisplayName("개별 상품 상세 조회 테스트")
  public void product_findById_test() throws Exception {
    //given
    List<Product> products = productDummyList();
    List<Option> options = optionDummyList(products);

    //stub
    Mockito.when(fakeStore.getProductList()).thenReturn(products);
    Mockito.when(fakeStore.getOptionList()).thenReturn(options);

    //when
    ResultActions result = mvc.perform(
        MockMvcRequestBuilders
            .get("/products/" + optionDummyList(products).get(0).getId())
            .contentType(MediaType.APPLICATION_JSON)
    );

    String responseBody = result.andReturn().getResponse().getContentAsString();
    System.out.println("테스트 : " + responseBody);

    // then
    result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
  }

}